// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
public class RestaurantBillingConsoleApp {

    // JDBC connection variables
    private static final String URL = "jdbc:mysql://localhost:3306/restaurent";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Arzumysql@6446";

    // Predefined dishes and prices
    private static final String[] DISHES = {"vada", "idly", "dosa", "parotta"};
    private static final double[] PRICES = {5.00, 10.00, 30.00, 15.00};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueOrdering = true; // This flag controls the loop for ordering multiple times

        while (continueOrdering) {
            // Display the menu with predefined dishes
            System.out.println("--------Menu-------:");
            for (int i = 0; i < DISHES.length; i++) {
                System.out.println((i + 1) + ". " + DISHES[i] + " - Rs " + PRICES[i]);
            }

            // Loop until the user enters a valid dish number
            int dishNumber = 0;
            while (true) {
                System.out.println("Enter the number of the dish you want to order:");
                dishNumber = scanner.nextInt();

                if (dishNumber >= 1 && dishNumber <= DISHES.length) {
                    break; // Valid number entered, exit the loop
                } else {
                    System.out.println("Invalid number. Please enter a number between 1 and " + DISHES.length);
                }
            }

            // Get the chosen dish and its price
            String chosenDish = DISHES[dishNumber - 1];
            double pricePerDish = PRICES[dishNumber - 1];

            // Ask the user for the quantity
            System.out.println("Enter quantity:");
            int quantity = scanner.nextInt();

            // Calculate the total amount
            double totalAmount = pricePerDish * quantity;

            // Store the order in the database
            saveOrderToDatabase(chosenDish, quantity, totalAmount);

            // Display the result to the user
            System.out.println("Order saved successfully!");
            System.out.println("Dish: " + chosenDish + ", Quantity: " + quantity + ", Total: Rs " + totalAmount);

            // Ask if the user wants to place another order
            System.out.println("Do you want to add another order? (Enter 1 for Yes, 0 for No)");
            int userResponse = scanner.nextInt();

            if (userResponse == 0) {
                continueOrdering = false; // Exit the loop if the user says "No"
            }
        }

        System.out.println("Thank you for using the Restaurant Billing System!");
    }

    // Method to save the order in the MySQL database
    private static void saveOrderToDatabase(String dishName, int quantity, double totalAmount) {
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            // SQL query to insert the order into the Orders table
            String sql = "INSERT INTO Orders (dish_name, quantity, total_amount) VALUES (?, ?, ?)";
            PreparedStatement state = con.prepareStatement(sql);
            
            // Set the values for the query
            state.setString(1, dishName);
            state.setInt(2, quantity);
            state.setDouble(3, totalAmount);

            // Execute the update
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
