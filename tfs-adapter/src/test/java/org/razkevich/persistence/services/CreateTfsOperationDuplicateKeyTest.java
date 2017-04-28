package org.razkevich.persistence.services;

import org.apache.commons.io.FileUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.razkevich.persistence.model.TfsRsProcessorKey;
import ru.sbrf.ufs.eu.tfs.ReSendFileNfType;
import ru.sbrf.ufs.eu.tfs.SendConfigNfType;
import ru.sbrf.ufs.eu.tfs.SendFileStatusNfType;
import org.razkevich.persistence.model.TfsOperation;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.razkevich.persistence.services.CreateTfsOperationTest.getUUID;

public class CreateTfsOperationDuplicateKeyTest {

	private JdbcDataSource jdbcDataSource;
	TfsOperation tfsOperation1 = new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(SendFileStatusNfType.class, getUUID(), getUUID()));
	TfsOperation tfsOperation2 = new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(SendConfigNfType.class, getUUID(), getUUID()));
	TfsOperation tfsOperation3 = new TfsOperation(getUUID(), getUUID(), new TfsRsProcessorKey(ReSendFileNfType.class, getUUID(), getUUID()));

	@Before
	public void setUpConnection() throws IOException, SQLException {
		String sql = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource(CreateTfsOperationTest.SQL_SCRIPT).getFile()));
		jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.setURL("jdbc:h2:~/test_tfs_adapter");
		jdbcDataSource.setUser("sa");
		jdbcDataSource.setPassword("sa");
		jdbcDataSource.getConnection().prepareStatement(CreateTfsOperationTest.TRUNCATE_H2_DATABASE).executeUpdate();
		jdbcDataSource.getConnection().prepareStatement(sql).executeUpdate();
	}

	@Test
	public void testCreateOperation() throws SQLException {
		TfsDbPersistenceService tfsDbPersistenceService = new TfsDbPersistenceService(jdbcDataSource);
		for (int i = 0; i < 10; i++) tfsDbPersistenceService.createTfsOperation(tfsOperation1);
		checkCountOfKeys(1, tfsOperation1);
		tfsDbPersistenceService.createTfsOperation(tfsOperation1);
		tfsDbPersistenceService.createTfsOperation(tfsOperation2);
		tfsDbPersistenceService.createTfsOperation(tfsOperation3);
		checkCountOfKeys(1, tfsOperation1);
		checkCountOfKeys(1, tfsOperation2);
		checkCountOfKeys(1, tfsOperation3);
		checkTotalCountOfKeys(3);

	}

	private void checkCountOfKeys(int expected, TfsOperation tfsOperation) throws SQLException {
		PreparedStatement stmt = jdbcDataSource.getConnection().prepareStatement("SELECT count(*) FROM tfs_rs_processor_key WHERE rs_class=? AND scenario_id=? AND business_process_name=?;");
		stmt.setString(1, tfsOperation.getTfsRsProcessorKey().getTfsRsClass().getName());
		stmt.setString(2, tfsOperation.getTfsRsProcessorKey().getTfsScenarioId());
		stmt.setString(3, tfsOperation.getTfsRsProcessorKey().getBusinessProcessName());
		ResultSet rs = stmt.executeQuery();
		assertTrue(rs.next());
		assertEquals(expected, rs.getInt(1));
	}

	private void checkTotalCountOfKeys(int expected) throws SQLException {
		PreparedStatement stmt = jdbcDataSource.getConnection().prepareStatement("SELECT count(*) FROM tfs_rs_processor_key;");
		ResultSet rs = stmt.executeQuery();
		assertTrue(rs.next());
		assertEquals(expected, rs.getInt(1));
	}
}

