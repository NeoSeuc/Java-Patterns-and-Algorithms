package core;

import java.sql.*;

/**
 * to use this code you need mysql-connector-java.jar
 * in the url variable you need change my database name (chat) to your
 */
public class JDBC {
    private Connection connection;
    private static JDBC instance = null;

    private JDBC() {};

    private JDBC(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static JDBC getInstance(String url, String user, String password) throws SQLException {
        if (instance == null) {
            instance = new JDBC(url, user, password);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getSelectResult(String table) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM "+table);
    }

    public int insertIntoTable(String table, String[] columns, String[] values) throws SQLException {
        String sql = "INSERT INTO "+table+" ("+String.join(", ", columns)+") VALUES (\""+String.join("\",\"", values)+"\")";
        return connection.createStatement().executeUpdate(sql);
    }

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost/chat?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "login";
        String password = "password";
        JDBC jdbc = JDBC.getInstance(url, user, password);
        jdbc.insertIntoTable("message", new String[]{"text", "sender"}, new String[]{"all ok", "java"});
        ResultSet res = jdbc.getSelectResult("message");



        while (res.next()) {
            System.out.println(res.getString(3)+" "+res.getString(2)+" "+res.getString(4));
        }
    }
}
