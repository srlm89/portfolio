package xxl.java.jooq;

import xxl.java.BasicProperties;
import org.jooq.ExecuteListener;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.tools.LoggerListener;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class JooqConfigurationFactory {

    private static DataSource dataSource = newDataSource();
    private static DefaultExecuteListenerProvider loggingListener = newLoggingListenerProvider();
    private static DataSourceConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);

    public static DefaultConfiguration create() {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setConnectionProvider(connectionProvider);
        configuration.setExecuteListenerProvider(loggingListener);
        return configuration;
    }

    private static DataSource newDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(BasicProperties.databaseUrl());
        dataSource.setUser(BasicProperties.databaseUser());
        dataSource.setPassword(BasicProperties.databasePassword());
        dataSource.setConnectTimeout(30);
        return dataSource;
    }

    private static DefaultExecuteListenerProvider newLoggingListenerProvider() {
        ExecuteListener listener = new LoggerListener();
        DefaultExecuteListenerProvider provider = new DefaultExecuteListenerProvider(listener);
        return provider;
    }
}
