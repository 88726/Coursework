package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        //connects to the database
openDatabase("CourseworkDatabase.db");

//This prepares the Jersey servlet, and changes the settings for this
        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        //This tells the Jetty server which port to use, and connects the servlet to the server
        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");


        //this attempts to start the server, displaying a success or error message
        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

            //displays a success message or, if there is an error, the code in the catch clause will produce an error message rather than allowing the program to crash
            System.out.println("Connected successfully");
        } catch (Exception exception) {
            System.out.println("Connection error" + exception.getMessage());
        }
    }

    private static void closeDatabase() {
        try {
            //closes the database file
            db.close();

            //displays a success message or, if there is an error, the code in the catch clause will produce an error message rather than allowing the program to crash
            System.out.println("Disconnected successfully");
        } catch (Exception exception) {
            System.out.println("Disconnection error" + exception.getMessage());
        }
    }
}
