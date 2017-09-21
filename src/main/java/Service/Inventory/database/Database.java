package Service.Inventory.database;

import java.sql.*;

/**
 * Created by Myles on 2/25/17.
 */
public class Database {

    private static Statement statement;
    private static Connection connection;
    private static String hostnameLogin;
    private static String passwordLogin;
    private static String address;
    private static String dbName;


    public Database(String address, String dbName, String hostnameLogin, String passwordLogin)  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection("jdbc:mysql://" + address + "/" + dbName + "?useSSL=false", hostnameLogin , passwordLogin));
            setStatement(getConnection().createStatement());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Database(int database)  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(database == 0) {
                setConnection(DriverManager.getConnection("jdbc:mysql://127.0.0.1/Inventory1?useSSL=false", "root", ""));
                setStatement(getConnection().createStatement());
            }
            else if(database == 1)
            {
                setConnection(DriverManager.getConnection("jdbc:mysql://127.0.0.1/Employee?useSSL=false", "root", ""));
                setStatement(getConnection().createStatement());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        Database.statement = statement;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Database.connection = connection;
    }

    public static String getHostnameLogin() {
        return hostnameLogin;
    }

    public static void setHostnameLogin(String hostnameLogin) {
        Database.hostnameLogin = hostnameLogin;
    }

    public static String getPasswordLogin() {
        return passwordLogin;
    }

    public static void setPasswordLogin(String passwordLogin) {
        Database.passwordLogin = passwordLogin;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Database.address = address;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        Database.dbName = dbName;
    }
}
