package org.example;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.io.Closeable;

public class CassandraConnection implements Closeable {

    private static CassandraConnection instance = new CassandraConnection();
    private Session session;
    private Cluster cluster;

    private CassandraConnection() {

    }

    public static synchronized CassandraConnection getInstance() {
        if (instance == null) {
            instance = new CassandraConnection();
        }
        return instance;
    }
    public Session getSession() {
        if (session != null) {
            return session;
        } else {
             cluster = Cluster.builder()
                    .addContactPoint("127.0.0.1")
                    .build();
            session = cluster.connect("task_manager");
            return session;
        }
    }

    @Override
    public void close()  {
        session.close();
        cluster.close();
    }
}
