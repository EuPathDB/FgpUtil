package org.gusdb.fgputil.db.platform;

import javax.sql.DataSource;

import org.gusdb.fgputil.db.pool.ConnectionPoolConfig;
import org.gusdb.fgputil.db.pool.DatabaseInstance;
import org.gusdb.fgputil.db.runner.SQLRunner;

public class DBPlatformTest {

  private class OracleConfig implements ConnectionPoolConfig {
    @Override public String getLogin() { return "ryan"; }
    @Override public String getPassword() { return "mypw"; }
    @Override public String getConnectionUrl() { return "url://mydb"; }
    @Override public SupportedPlatform getPlatformEnum() { return SupportedPlatform.ORACLE; }
	@Override public String getDriverInitClass() { return null; }
    @Override public short getMaxActive() { return 20; }
    @Override public short getMaxIdle() { return 20; }
    @Override public short getMinIdle() { return 5; }
    @Override public short getMaxWait() { return 2000; }
    @Override public boolean isShowConnections() { return true; }
    @Override public long getShowConnectionsInterval() { return 5; }
    @Override public long getShowConnectionsDuration() { return 30; }
  }
  
  private class PostgresConfig extends OracleConfig {
    @Override public SupportedPlatform getPlatformEnum() { return SupportedPlatform.POSTGRESQL; }
  }
  
  // Cannot make this a unit test because it would require both Postgres and Oracle drivers to succeed
  //@Test
  public void testDbPlatform() throws Exception {
    ConnectionPoolConfig appConfig = new OracleConfig();
    ConnectionPoolConfig userConfig = new PostgresConfig();
    
    DatabaseInstance appDb = new DatabaseInstance("APP", appConfig);
    DatabaseInstance userDb = new DatabaseInstance("USER", userConfig);
    
    appDb.initialize();
    userDb.initialize();
    
    // later, in actions, etc...
    DataSource appDs = appDb.getDataSource();
    DataSource userDs = userDb.getDataSource();
    
    SQLRunner runner = new SQLRunner(appDs, appDb.getPlatform().getValidationQuery());
    runner.executeStatement();
    runner = new SQLRunner(userDs, userDb.getPlatform().getValidationQuery());
    runner.executeStatement();
  }
  
}
