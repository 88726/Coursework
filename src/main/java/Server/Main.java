package Server;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void Main(String[] args) {
openDatabase("CourseworkDatabase.db");

        Controllers.Students.read();
        Controllers.Students.update("hannah", "Password", 1);
        Controllers.Students.read();
        closeDatabase();

    }
    //this will behave like a global variable for the following blocks
    public static Connection db = null;

    private static void openDatabase(String dbFile) {
            try {
            //loads the database driver
            Class.forName("org.sqlite.JDBC");

            //changing the settings of the database
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            //opens the database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());

            System.out.println("Connected successfully");
        } catch (Exception exception) {
            System.out.println("Connection error" + exception.getMessage());
        }
    }

    private static void closeDatabase() {
        try {
            db.close();

            System.out.println("Disconnected successfully");
        } catch (Exception exception) {
            System.out.println("Disconnection error" + exception.getMessage());
        }
    }
}
