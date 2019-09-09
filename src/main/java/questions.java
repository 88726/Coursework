import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class questions {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT questionID, questionName, questionPassword FROM questions");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int questionID = results.getInt(1);
                String questionName = results.getString(2);
                String questionPassword = results.getString(3);
                System.out.println(questionID + " " + questionName + " " + questionPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into questions (questionName, questionPassword) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String questionName, String questionPassword, int questionID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE questions SET questionName = ?, questionPassword = ? WHERE questionID = ?");

            ps.setString(1, questionName);
            ps.setString(2, questionPassword);
            ps.setInt(3, questionID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int questionID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM questions WHERE questionID = ?");

            ps.setInt(1, questionID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
