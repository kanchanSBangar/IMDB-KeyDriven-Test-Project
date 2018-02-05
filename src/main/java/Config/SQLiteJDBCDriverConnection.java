package Config;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class SQLiteJDBCDriverConnection {

    private static Boolean hasData = false;
    private static Connection conn;
    private static Statement statement = null;

    public static void connect() throws ClassNotFoundException, SQLException {

        conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void createTable(){
        try {
            statement = conn.createStatement();
            String sql = "CREATE TABLE MOVIELIST " +
                    "(RANK INT PRIMARY KEY NOT NULL," +
                    " TITLE TEXT NOT NULL, " +
                    " YEAR INT, " +
                    " RATING         REAL)";
            statement.executeUpdate(sql);
            statement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void  insertData(int rank, String title, int year, double rating) throws SQLException {
        statement = conn.createStatement();
        String sql = "INSERT INTO MOVIELIST (RANK,TITLE,YEAR,RATING) " +
                "VALUES ("+rank+",\""+title+"\","+year+","+rating+");";
        statement.executeUpdate(sql);
        statement.close();
    }

    public static void readData() throws SQLException {
        statement = conn.createStatement();
        ResultSet rs = statement.executeQuery( "SELECT * FROM MOVIELIST;" );

        while ( rs.next() ) {
            int id = rs.getInt("RANK");
            String  title = rs.getString("TITLE");
            int year = rs.getInt("YEAR");
            float rating = rs.getFloat("RATING");
            if(rs.isFirst()) {
                System.out.println("ID\t Title \t\t\t Year \t Rating");
            }
            System.out.print( id +"\t"+title+ "\t"+ year+"\t"+rating);
            System.out.println();
        }
    }

    public static void dropTable() throws SQLException {
        statement = conn.createStatement();
        String sql = "DROP TABLE MOVIELIST";
        statement.executeUpdate(sql);


    }



    public static void closeDB() throws SQLException {
        conn.close();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        connect();
        readData();
        conn.close();
    }
}
