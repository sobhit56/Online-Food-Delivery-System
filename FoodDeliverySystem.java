import java.util.Scanner;

// Parent Class (Inheritance)
class Person {
    private String customerName;

    Person(String customerName) {
        this.customerName = customerName;
    }

    String getCustomerName() {
        return customerName;
    }
}

// FoodItem Class
class FoodItem {
    private String itemName;
    private double price;

    FoodItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    String getItemName() {
        return itemName;
    }

    double getPrice() {
        return price;
    }
}

// Restaurant Class
class Restaurant {
    private String name;
    private FoodItem[] menu;

    Restaurant(String name, FoodItem[] menu) {
        this.name = name;
        this.menu = menu;
    }

    void displayMenu() {
        System.out.println("\nMenu of " + name);

        for (int i = 0; i < menu.length; i++) {
            System.out.println(
                    (i + 1) + ". "
                    + menu[i].getItemName()
                    + " - Rs." + menu[i].getPrice()
            );
        }
    }
}

// Order Class (Inheritance)
class Order extends Person {

    private FoodItem[] items = new FoodItem[10];
    private int[] quantities = new int[10];
    private int count = 0;

    private double subtotal = 0;
    private double deliveryCharge = 0;
    private double tax = 0;
    private double total = 0;

    Order(String customerName) {
        super(customerName);
    }

    void addItem(FoodItem item, int quantity) {

        if (count >= items.length) {
            throw new IllegalStateException(
                    "Maximum number of items reached."
            );
        }

        items[count] = item;
        quantities[count] = quantity;
        count++;
    }

    void calculateTotal() {

        subtotal = 0;

        for (int i = 0; i < count; i++) {
            subtotal += items[i].getPrice() * quantities[i];
        }

        // Free delivery for orders above Rs.500
        if (subtotal > 500) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 50;
        }

        // GST 5%
        tax = subtotal * 0.05;

        total = subtotal + deliveryCharge + tax;

        }
        

    // Polymorphism - Method 1
    void displayOrder() {

        System.out.println("\nOrder Summary:");
        System.out.println("-----------------------");

        for (int i = 0; i < count; i++) {
            System.out.println(
                    items[i].getItemName()
                    + " x" + quantities[i]
                    + " = Rs."
                    + (items[i].getPrice() * quantities[i])
            );
        }

        System.out.println("-----------------------");
        System.out.println("Customer Name: " + getCustomerName());
        System.out.println("Subtotal: Rs." + subtotal);
        System.out.println("Delivery Charge: Rs." + deliveryCharge);
        System.out.println("Tax (5%): Rs." + tax);
        System.out.println("Total Amount: Rs." + total);
    }

    // Polymorphism - Method Overloading
    void displayOrder(String customerName) {

        System.out.println("\nCustomer Name: " + customerName);

        displayOrder();
    }

    String generateOrderDetails() {

    StringBuilder details = new StringBuilder();

    details.append("================================\n");
    details.append("Customer Name: ")
           .append(getCustomerName())
           .append("\n");

    details.append("Order Details:\n");

    for (int i = 0; i < count; i++) {

        details.append(
                items[i].getItemName()
                + " x" + quantities[i]
                + " = Rs."
                + (items[i].getPrice() * quantities[i])
                + "\n"
        );
    }

    details.append("--------------------------------\n");

    details.append("Subtotal: Rs.")
           .append(subtotal)
           .append("\n");

    details.append("Delivery Charge: Rs.")
           .append(deliveryCharge)
           .append("\n");

    details.append("Tax (5%): Rs.")
           .append(tax)
           .append("\n");

    details.append("Total Amount: Rs.")
           .append(total)
           .append("\n");

    details.append("================================");

    return details.toString();
}
}

// Main Class
public class FoodDeliverySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create Menu
        FoodItem[] menu = {
                new FoodItem("Burger", 100),
                new FoodItem("Pizza", 300),
                new FoodItem("Pasta", 200),
                new FoodItem("Cold Drink", 50)
        };

        Restaurant restaurant =
                new Restaurant("Food Hub", menu);

        // Customer Name
        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            sc.close();
            return;
        }

        // Create Order
        Order order = new Order(customerName);

        // Display Menu
        restaurant.displayMenu();

        int numberOfItems;

        // Get number of items
        while (true) {

            try {
                System.out.print(
                        "\nEnter number of items you want to order: "
                );

                numberOfItems = sc.nextInt();

                if (numberOfItems <= 0) {
                    System.out.println(
                            "Number of items must be greater than 0."
                    );
                    continue;
                }

                if (numberOfItems > 10) {
                    System.out.println(
                            "You can order a maximum of 10 items."
                    );
                    continue;
                }

                break;

            } catch (Exception e) {

                System.out.println(
                        "Invalid input! Please enter a number."
                );

                sc.nextLine();
            }
        }

        // Select Items
        for (int i = 0; i < numberOfItems; i++) {

            while (true) {

                try {

                    System.out.print("Enter item number: ");
                    int choice = sc.nextInt();

                    if (choice < 1 || choice > menu.length) {
                        System.out.println(
                                "Invalid menu choice! "
                                + "Please select a valid item."
                        );
                        continue;
                    }

                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();

                    if (quantity <= 0) {
                        System.out.println(
                                "Quantity must be greater than 0."
                        );
                        continue;
                    }

                    order.addItem(
                            menu[choice - 1],
                            quantity
                    );

                    break;

                } catch (Exception e) {

                    System.out.println(
                            "Invalid input! Please enter numbers only."
                    );

                    sc.nextLine();
                }
            }
        }

        // Calculate Bill
       order.calculateTotal();

      order.displayOrder(customerName);

    // Save order to file
    OrderFileManager.saveOrder(order.generateOrderDetails());

    // View previous orders
    System.out.print(
        "\nDo you want to view previous orders? (yes/no): "
);

String answer = sc.next();

if (answer.equalsIgnoreCase("yes")) {
    OrderFileManager.viewOrders();
}


    sc.close();

    }
}