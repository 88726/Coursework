import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Students {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT userID, userName, userPassword FROM users");

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

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into users (userName, userPassword) VALUES (?,?)");

            ps.setString(1, "hannah123" );
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
    public static void update(String userName, String userPassword, int userID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE users SET userName = ?, userPassword = ? WHERE userID = ?");

            ps.setString(1, userName );
            ps.setString(2, userPassword);
            ps.setInt(3, userID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
    public static void delete(int userID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM users WHERE userID = ?");

            ps.setInt(1, userID );

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}

