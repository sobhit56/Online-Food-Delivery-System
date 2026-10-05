import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class OrderFileManager {

    private static final String FILE_NAME = "orders.txt";

    // Save order details to file
    static void saveOrder(String orderDetails) {

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.write(orderDetails);
            writer.write("\n\n");

            System.out.println("\nOrder saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error while saving order: " + e.getMessage()
            );
        }
    }

    // Display previous orders
    static void viewOrders() {

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            boolean found = false;

            System.out.println("\n========== ORDER HISTORY ==========");

            while ((line = reader.readLine()) != null) {

                System.out.println(line);
                found = true;
            }

            if (!found) {
                System.out.println("No previous orders found.");
            }

            System.out.println("===================================");

        } catch (IOException e) {

            System.out.println("No order history found.");
        }
    }
}