package org.razkevich.persistence.services;

import org.razkevich.persistence.model.TfsOperationStatus;
import org.razkevich.persistence.model.TfsRsProcessorKey;
import ru.sbrf.ufs.eu.tfs.BasicRqType;
import org.razkevich.business.model.TfsRuntimeException;
import org.razkevich.persistence.model.TfsOperation;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;

//todo: [] reqs as string constants
@SuppressWarnings("unchecked")
public class TfsDbPersistenceService implements TfsPersistenceService {

	private final DataSource dataSource;

	public TfsDbPersistenceService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void createTfsOperation(TfsOperation data) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("SELECT tfs_operation_seq.nextval FROM dual;");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Long seqId = rs.getLong(1);
			stmt = conn.prepareStatement("INSERT INTO tfs_operation (id, creation_date, " +
					"update_date, status, rq_uid, file_uid) VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setLong(1, seqId);
			stmt.setTimestamp(2, data.getCreationDate() == null ? null : new java.sql.Timestamp(data.getCreationDate().getTime().getTime()));
			stmt.setTimestamp(3, data.getUpdateDate() == null ? null : new java.sql.Timestamp(data.getUpdateDate().getTime().getTime()));
			stmt.setString(4, data.getStatus() == null ? null : data.getStatus().name());
			stmt.setString(5, data.getRqUID());
			stmt.setString(6, data.getFileUID());
			stmt.executeUpdate();
			data.setId(seqId);
			assignKey(conn, data);
			conn.commit();
		} catch (Exception e) {
			rollbackQuietly(conn);
			throw new TfsRuntimeException(e);
		} finally {
			closeNonQuietly(conn, stmt);
		}
	}

	@Override
	public void updateTfsOperation(TfsOperation data) {
		Connection conn = null;
		PreparedStatement stmt = null;
		if (data.getId() == null) {
			throw new IllegalArgumentException();
		}
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("UPDATE tfs_operation SET creation_date = ?, " +
					"update_date = ?, status = ?, rq_uid = ?, file_uid = ? WHERE ID = ?");
			stmt.setTimestamp(1, new java.sql.Timestamp(data.getCreationDate().getTime().getTime()));
			stmt.setTimestamp(2, new java.sql.Timestamp(data.getUpdateDate().getTime().getTime()));
			stmt.setString(3, data.getStatus().name());
			stmt.setString(4, data.getRqUID());
			stmt.setString(5, data.getFileUID());
			stmt.setLong(6, data.getId());
			stmt.executeUpdate();
			assignKey(conn, data);
			conn.commit();
		} catch (Exception e) {
			rollbackQuietly(conn);
			throw new TfsRuntimeException(e);
		} finally {
			closeNonQuietly(conn, stmt);
		}
	}

	@Override
	public TfsOperation getTfsOperation(String rqUID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		Calendar creationDateCal = Calendar.getInstance(), updateDateCal = Calendar.getInstance();
		Timestamp creationDate, updateDate;
		TfsOperationStatus tfsOperationStatus = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT " +
					"tfs_operation.id id, " +
					"tfs_operation.creation_date creation_date, " +
					"tfs_operation.update_date update_date, " +
					"tfs_operation.status , " +
					"tfs_operation.rq_uid rq_uid, " +
					"tfs_operation.file_uid file_uid, " +
					"tfs_rs_processor_key.rs_class rs_class, " +
					"tfs_rs_processor_key.scenario_id scenario_id, " +
					"tfs_rs_processor_key.business_process_name bp_name " +
					"FROM tfs_operation " +
					"INNER JOIN " +
					"tfs_rs_processor_key " +
					"ON tfs_operation.tfs_rs_processor_key_id = tfs_rs_processor_key.id " +
					"WHERE tfs_operation.rq_uid = ?");
			stmt.setString(1, rqUID);
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			TfsOperation tfsOperation = new TfsOperation(rs.getString("rq_uid"), rs.getString("file_uid"), new TfsRsProcessorKey(
					(Class<? extends BasicRqType>) Class.forName(rs.getString("rs_class")),
					rs.getString("scenario_id"),
					rs.getString("bp_name")));
			tfsOperation.setId(rs.getLong("id"));
			if ((creationDate = rs.getTimestamp("creation_date")) != null) {
				creationDateCal.setTime(creationDate);
				tfsOperation.setCreationDate(creationDateCal);
			}
			if ((updateDate = rs.getTimestamp("update_date")) != null) {
				updateDateCal.setTime(updateDate);
				tfsOperation.setUpdateDate(updateDateCal);
			}
			try {
				tfsOperationStatus = TfsOperationStatus.valueOf(rs.getString("status"));
			} catch (RuntimeException ignored) {
			}
			tfsOperation.setStatus(tfsOperationStatus);
			return tfsOperation;
		} catch (Exception e) {
			throw new TfsRuntimeException(e);
		} finally {
			closeNonQuietly(conn, stmt);
		}
	}

	private static void assignKey(Connection conn, TfsOperation data) throws SQLException {
		if (data.getTfsRsProcessorKey() == null) {
			conn.rollback();
			throw new TfsRuntimeException("tfsRsProcessorKey must not be null");
		}
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT id FROM tfs_rs_processor_key WHERE rs_class = ? AND scenario_id = ? AND business_process_name = ?");
		stmt.setString(1, data.getTfsRsProcessorKey().getTfsRsClass() == null ? null : data.getTfsRsProcessorKey().getTfsRsClass().getName());
		stmt.setString(2, data.getTfsRsProcessorKey().getTfsScenarioId());
		stmt.setString(3, data.getTfsRsProcessorKey().getBusinessProcessName());
		ResultSet rs = stmt.executeQuery();
		Long tfsProcessorKeyId;
		if (rs.next()) {
			tfsProcessorKeyId = rs.getLong(1);
		} else {
			stmt = conn.prepareStatement("SELECT tfs_rs_processor_key_seq.nextval FROM dual;");
			rs = stmt.executeQuery();
			rs.next();
			tfsProcessorKeyId = rs.getLong(1);
			stmt = conn.prepareStatement("INSERT INTO tfs_rs_processor_key " +
					"(id, rs_class, scenario_id, business_process_name) VALUES  (?, ?, ?, ?)");
			stmt.setLong(1, tfsProcessorKeyId);
			stmt.setString(2, data.getTfsRsProcessorKey().getTfsRsClass().getName());
			stmt.setString(3, data.getTfsRsProcessorKey().getTfsScenarioId());
			stmt.setString(4, data.getTfsRsProcessorKey().getBusinessProcessName());
			stmt.executeUpdate();
		}
		stmt = conn.prepareStatement("UPDATE tfs_operation SET tfs_rs_processor_key_id = ? WHERE id = ?");
		stmt.setLong(1, tfsProcessorKeyId);
		stmt.setLong(2, data.getId());
		stmt.executeUpdate();
	}

	private static void closeNonQuietly(Connection conn, PreparedStatement stmt) {
		try {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			throw new TfsRuntimeException("Failed to close resources", e);
		}
	}

	private void rollbackQuietly(Connection conn) {
		try {
			if (conn != null) conn.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
