package ConnectionPool;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionPool {

    private String username;
    private String password;
    private String driverURL;
    private int preconnectCount;
    private int maxConnections;
    private int maxIdleConnections;

    private int ConnectCount;
    private List<Connection> usedConnections;
    private List<Connection> freeConnections;

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private ConnectionPool() {
        readConfiguration();
        try {
            freeConnections = new ArrayList<Connection>();
            usedConnections = new ArrayList<Connection>();

            for (int i = 0; i < preconnectCount; i++) {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopmuzikeopreme","root","Metar123");
                System.out.println("Connected to MySQL database");
                freeConnections.add(connection);
            }
            ConnectCount = preconnectCount;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void readConfiguration() {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("ConnectionPool.ConnectionPool");
        driverURL = bundle.getString("driverURL");
        username = bundle.getString("username");
        password = bundle.getString("password");
        preconnectCount = 0;
        maxIdleConnections = 5;
        maxConnections = 5;
        try {
            preconnectCount = Integer.parseInt(bundle.getString("preconnectCount"));
            maxIdleConnections = Integer.parseInt(bundle.getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(bundle.getString("maxConnections"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public synchronized Connection checkOut() throws SQLException {

        Connection connection = null;
        if (freeConnections.size() > 0) {
            connection = freeConnections.remove(0);
            usedConnections.add(connection);
        } else {
            if (ConnectCount < maxConnections) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopmuzikeopreme","root","Metar123");
                System.out.println("Connected to MySQL database");
                usedConnections.add(connection);
                ConnectCount++;
            } else {
                try {
                    wait();
                    connection = freeConnections.remove(0);
                    usedConnections.add(connection);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return connection;
    }

    public synchronized void checkIn(Connection connection){

        if(connection==null){
            return;
        }
        if(usedConnections.remove(connection)){
            freeConnections.add(connection);
            while (freeConnections.size()>maxIdleConnections){
                int lastone =freeConnections.size() -1;
                Connection con= freeConnections.remove(lastone);
                try {
                con.close();
                }catch (SQLException exception){
                    exception.printStackTrace();
                }
            }
            notify();
        }
    }
}

