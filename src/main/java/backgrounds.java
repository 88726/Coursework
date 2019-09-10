import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class backgrounds {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT backgroundID, backgroundPrice, backgroundImage FROM backgrounds");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int backgroundID = results.getInt(1);
                String backgroundPrice = results.getString(2);
                String backgroundImage = results.getString(3);
                System.out.println(backgroundID + " " + backgroundPrice + " " + backgroundImage);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into backgrounds (backgroundPrice, backgroundImage) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String backgroundPrice, String backgroundImage, int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE backgrounds SET backgroundPrice = ?, backgroundImage = ? WHERE backgroundID = ?");

            ps.setString(1, backgroundPrice);
            ps.setString(2, backgroundImage);
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
