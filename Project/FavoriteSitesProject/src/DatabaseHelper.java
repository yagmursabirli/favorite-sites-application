import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/se2224_Project";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM userinfo WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addVisit(String username, String country, String city, int year, String season, String feature, String comment, int rating) {
        String query = "INSERT INTO visits (username, country, city, year, season, feature, comment, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, country);
            statement.setString(3, city);
            statement.setInt(4, year);
            statement.setString(5, season);
            statement.setString(6, feature);
            statement.setString(7, comment);
            statement.setInt(8, rating);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   public static void deleteVisit(int visitId) {
       Connection con = getConnection();
       PreparedStatement psSharedVisits = null;
       PreparedStatement psVisits = null;
       try {

           String sqlSharedVisits = "DELETE FROM shared_visits WHERE visit_id = ?";
           psSharedVisits = con.prepareStatement(sqlSharedVisits);
           psSharedVisits.setInt(1, visitId);
           psSharedVisits.executeUpdate();


           String sqlVisits = "DELETE FROM visits WHERE visit_id = ?";
           psVisits = con.prepareStatement(sqlVisits);
           psVisits.setInt(1, visitId);
           psVisits.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           try {
               if (psSharedVisits != null) psSharedVisits.close();
               if (psVisits != null) psVisits.close();
               if (con != null) con.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }


    public static void updateVisit(int visitId, String country, String city, int year, String season, String feature, String comment, int rating) {
        String query = "UPDATE visits SET country = ?, city = ?, year = ?, season = ?, feature = ?, comment = ?, rating = ? WHERE visit_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, country);
            statement.setString(2, city);
            statement.setInt(3, year);
            statement.setString(4, season);
            statement.setString(5, feature);
            statement.setString(6, comment);
            statement.setInt(7, rating);
            statement.setInt(8, visitId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getBestFoodLocations() {
        String query = "SELECT country, rating FROM visits WHERE feature = 'Food' ORDER BY rating DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getVisitsByYear(int year) {
        String query = "SELECT * FROM visits WHERE year = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, year);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getMostVisitedCountries() {
        String query = "SELECT country, COUNT(country) as visit_count FROM visits GROUP BY country ORDER BY visit_count DESC";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getCountriesVisitedInSpring() {
        String query = "SELECT country FROM visits WHERE season = 'spring'";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void shareVisit(String username, String friendUsername, int visitId) {
        String query = "INSERT INTO shared_visits (username, friend_username, visit_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, friendUsername);
            statement.setInt(3, visitId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getSharedVisits(String username) {
        String query = "SELECT * FROM shared_visits WHERE friend_username = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getVisitById(int visitId) {
        String query = "SELECT * FROM visits WHERE visit_id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, visitId);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isVisitIdValid(int visitId) {
        String query = "SELECT COUNT(*) FROM visits WHERE visit_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se2224_Project","root", "12345678");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, visitId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean isUsernameValid(String username) {
        String query = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se2224_Project", "root", "12345678");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}



