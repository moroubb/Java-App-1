package repository;

import domain.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final String JDBC_URL = "jdbc:sqlite:data/test_db.db";

    private static Connection conn = null;

    private List<Recipe>recipes=new ArrayList<>();


    public Repository() {
        // Initialize the repository by fetching data from the database
        fetchRoutesFromDatabase();
    }

    public static Connection getConnection() {
        if (conn == null)
            openConnection();
        return conn;
    }

    private void fetchRoutesFromDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM recipes";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int cookt=resultSet.getInt("cookingtime");
                String incredients=resultSet.getString("incredients");

               Recipe r=new Recipe(name,cookt,incredients);
               recipes.add(r);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void openConnection()
    {
        try
        {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnection()
    {
        try
        {
            conn.close();
            conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void add(Recipe d) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "INSERT INTO Recipes (name, cookingtime,incredients) " +
                    "VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, d.getName());
                preparedStatement.setInt(2, d.getCookingtime());
                preparedStatement.setString(3, d.getIncredients());

                preparedStatement.executeUpdate();
            }

            recipes.add(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getDocuments() {
        return recipes;
    }

    public List<Recipe> showRecipes()
    {
        List<Recipe> docList = getDocuments();
        List<Recipe> result = docList.stream()
                .sorted(Comparator.comparing(Recipe::getName))
                .collect(Collectors.toList());

        List<Recipe> newList = new ArrayList<>();
        for (Recipe d:result)
        {
            newList.add(d);
        }

        return newList;

    }

    public void remove(Recipe d) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "DELETE FROM Recipes WHERE name = ? AND cookingtime = ? AND incredients = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, d.getName());
                preparedStatement.setInt(2, d.getCookingtime());
                preparedStatement.setString(3, d.getIncredients());

                preparedStatement.executeUpdate();
            }

            // Remove the document from the in-memory list or data source
            recipes.remove(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Recipe> search(String searchTerm) {
        ObservableList<Recipe> searchResults = FXCollections.observableArrayList();
        String query = "SELECT * FROM Recipes WHERE name LIKE ? OR cookingtime LIKE ? OR incredients LIKE ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String wildcardSearchTerm = "%" + searchTerm + "%";
            preparedStatement.setString(1, wildcardSearchTerm);
            preparedStatement.setString(2, wildcardSearchTerm);
            preparedStatement.setString(3, wildcardSearchTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    Integer cookingtime = resultSet.getInt("cookingtime");
                    String incredients = resultSet.getString("incredients");
                    Recipe r=new Recipe(name,cookingtime,incredients);
                    searchResults.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }


}
