import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
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

            System.out.println("Disonnected successfully");
        } catch (Exception exception) {
            System.out.println("Disonnection error" + exception.getMessage());
        }
    }

    public static void read() {
        openDatabase("CourseworkDatabase.db");
        try {
            PreparedStatement ps = db.prepareStatement("SELECT userID, userName, userPassword FROM users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int userID = results.getInt(1);
                String userName = results.getString(2);
                String userPassword = results.getString(3);
                System.out.println(userID + " " + userName + " " + userPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }
        closeDatabase();
    }

    public static void insert() {
        openDatabase("CourseworkDatabase.db");
        try {
            PreparedStatement ps = db.prepareStatement("INSERT into users (userName, userPassword) VALUES (?,?)");

            ps.setString(1, "hannah123" );
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }
        closeDatabase();
    }
}
