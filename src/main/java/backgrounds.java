import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class backgrounds {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT backgroundID, backgroundName, backgroundPassword FROM backgrounds");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int backgroundID = results.getInt(1);
                String backgroundName = results.getString(2);
                String backgroundPassword = results.getString(3);
                System.out.println(backgroundID + " " + backgroundName + " " + backgroundPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into backgrounds (backgroundName, backgroundPassword) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String backgroundName, String backgroundPassword, int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE backgrounds SET backgroundName = ?, backgroundPassword = ? WHERE backgroundID = ?");

            ps.setString(1, backgroundName);
            ps.setString(2, backgroundPassword);
            ps.setInt(3, backgroundID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM backgrounds WHERE backgroundID = ?");

            ps.setInt(1, backgroundID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
