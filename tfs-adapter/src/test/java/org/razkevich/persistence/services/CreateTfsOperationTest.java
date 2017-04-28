package org.razkevich.persistence.services;

import org.apache.commons.io.FileUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.razkevich.persistence.model.TfsOperationStatus;
import org.razkevich.persistence.model.TfsRsProcessorKey;
import ru.sbrf.ufs.eu.tfs.ReSendFileNfType;
import ru.sbrf.ufs.eu.tfs.SendConfigNfType;
import ru.sbrf.ufs.eu.tfs.SendErrorNfType;
import ru.sbrf.ufs.eu.tfs.SendFileStatusNfType;
import org.razkevich.persistence.model.TfsOperation;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateTfsOperationTest {

	public static final String TRUNCATE_H2_DATABASE = "DROP ALL OBJECTS", SQL_SCRIPT = "db_migration_tfs/r01_01_00/v0.0.01__tfs_ddl.sql";
	private TfsOperation tfsOperation;
	private JdbcDataSource jdbcDataSource;

	public CreateTfsOperationTest(TfsOperation tfsOperation) {
		this.tfsOperation = tfsOperation;
	}

	@Parameters
	public static Collection<TfsOperation> getParams() throws Exception {
		List<TfsOperation> tfsOperations = new ArrayList<TfsOperation>();
		tfsOperations.add(new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(SendFileStatusNfType.class, getUUID(), getUUID())));
		tfsOperations.add(new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(SendConfigNfType.class, getUUID(), getUUID())));
		tfsOperations.add(new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(ReSendFileNfType.class, getUUID(), getUUID())));
		return tfsOperations;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Before
	public void setUpConnection() throws IOException, SQLException {
		String sql = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource(SQL_SCRIPT).getFile()));
		jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL("jdbc:h2:~/test_tfs_adapter");
		jdbcDataSource.setUser("sa");
		jdbcDataSource.setPassword("sa");
		jdbcDataSource.getConnection().prepareStatement(TRUNCATE_H2_DATABASE).executeUpdate();
		jdbcDataSource.getConnection().prepareStatement(sql).executeUpdate();
	}

	@Test
	public void testCreateOperation() throws SQLException {
		TfsDbPersistenceService tfsDbPersistenceService = new TfsDbPersistenceService(jdbcDataSource);
		tfsDbPersistenceService.createTfsOperation(tfsOperation);
		checkWithSql();
		checkWithDbPersistenceService(tfsDbPersistenceService);
		updateEntityWithoutChangingTfsKey();
		tfsDbPersistenceService.updateTfsOperation(tfsOperation);
		checkWithSql();
		checkWithDbPersistenceService(tfsDbPersistenceService);
		updateEntityWithChangingTfsKey();
		tfsDbPersistenceService.updateTfsOperation(tfsOperation);
		checkWithSql();
		checkWithDbPersistenceService(tfsDbPersistenceService);
	}

	private void updateEntityWithoutChangingTfsKey() {
		tfsOperation.setStatus(TfsOperationStatus.COMPLETED_WITH_ERROR);
		tfsOperation.setUpdateDate(Calendar.getInstance());
		tfsOperation.setFileUID(getUUID());
		tfsOperation.setRqUID(getUUID());
	}

	private void updateEntityWithChangingTfsKey() {
		updateEntityWithoutChangingTfsKey();
		tfsOperation.setTfsRsProcessorKey(new TfsRsProcessorKey(SendErrorNfType.class, getUUID(), getUUID()));
	}

	private void checkWithDbPersistenceService(TfsDbPersistenceService tfsDbPersistenceService) {
		TfsOperation dbOperation = tfsDbPersistenceService.getTfsOperation(tfsOperation.getRqUID());
		assertNotNull(dbOperation);
		assertEquals(dbOperation.getTfsRsProcessorKey().getBusinessProcessName(), dbOperation.getTfsRsProcessorKey().getBusinessProcessName());
		assertEquals(dbOperation.getTfsRsProcessorKey().getTfsScenarioId(), tfsOperation.getTfsRsProcessorKey().getTfsScenarioId());
		assertEquals(dbOperation.getTfsRsProcessorKey().getTfsRsClass(), tfsOperation.getTfsRsProcessorKey().getTfsRsClass());
		assertEquals(dbOperation.getFileUID(), tfsOperation.getFileUID());
		assertEquals(dbOperation.getRqUID(), tfsOperation.getRqUID());
		assertEquals(dbOperation.getStatus(), tfsOperation.getStatus());
		assertEquals(dbOperation.getCreationDate(), tfsOperation.getCreationDate());
		assertEquals(dbOperation.getUpdateDate(), tfsOperation.getUpdateDate());
	}

	private void checkWithSql() throws SQLException {
		PreparedStatement stmt = jdbcDataSource.getConnection().prepareStatement("SELECT creation_date,status,rq_uid,file_uid,tfs_rs_processor_key_id FROM tfs_operation;");
		ResultSet rs = stmt.executeQuery();
		assertEquals(true, rs.next());
		assertEquals(tfsOperation.getCreationDate().getTimeInMillis(), rs.getTimestamp(1).getTime());
		assertEquals(tfsOperation.getStatus(), TfsOperationStatus.valueOf(rs.getString(2)));
		assertEquals(tfsOperation.getRqUID(), rs.getString(3));
		assertEquals(tfsOperation.getFileUID(), rs.getString(4));
		assertNotNull(rs.getString(5));
		stmt = jdbcDataSource.getConnection().prepareStatement("SELECT count(*) FROM tfs_rs_processor_key WHERE rs_class=? AND scenario_id=? AND business_process_name=?;");
		stmt.setString(1, tfsOperation.getTfsRsProcessorKey().getTfsRsClass().getName());
		stmt.setString(2, tfsOperation.getTfsRsProcessorKey().getTfsScenarioId());
		stmt.setString(3, tfsOperation.getTfsRsProcessorKey().getBusinessProcessName());
		rs = stmt.executeQuery();
		assertEquals(true, rs.next());
	}


}

