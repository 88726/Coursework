import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Questions {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT questionID, question, answer FROM questions");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int questionID = results.getInt(1);
                String question = results.getString(2);
                String answer = results.getString(3);
                System.out.println(questionID + " " + question + " " + answer);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into questions (question, answer) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String question, String answer, int questionID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE questions SET question = ?, answer = ? WHERE questionID = ?");

            ps.setString(1, question);
            ps.setString(2, answer);
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
