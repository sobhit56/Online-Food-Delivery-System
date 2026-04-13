import java.util.Scanner;

// FoodItem Class
class FoodItem {
    String itemName;
    double price;

    // Constructor
    FoodItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    // Getters
    String getItemName() {
        return itemName;
    }

    double getPrice() {
        return price;
    }
}

// Restaurant Class
class Restaurant {
    String name;
    FoodItem[] menu;

    Restaurant(String name, FoodItem[] menu) {
        this.name = name;
        this.menu = menu;
    }

    // Display Menu
    void displayMenu() {
        System.out.println("\nMenu of " + name);
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i].getItemName() + " - ₹" + menu[i].getPrice());
        }
    }
}

// Order Class
class Order {
    FoodItem[] items = new FoodItem[10];
    int[] quantities = new int[10];
    int count = 0;

    double subtotal = 0;
    double deliveryCharge = 0;
    double tax = 0;
    double total = 0;

    // Add item
    void addItem(FoodItem item, int quantity) {
        items[count] = item;
        quantities[count] = quantity;
        count++;
    }

    // Calculate bill
    void calculateTotal() {
        subtotal = 0;

        for (int i = 0; i < count; i++) {
            subtotal += items[i].getPrice() * quantities[i];
        }

        // Delivery logic
        if (subtotal > 500) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 50;
        }

        // Tax 5%
        tax = subtotal * 0.05;

        total = subtotal + deliveryCharge + tax;
    }

    // Display summary
    void displayOrder() {
        System.out.println("\nOrder Summary:");
        System.out.println("-----------------------");

        for (int i = 0; i < count; i++) {
            System.out.println(items[i].getItemName() + " x" + quantities[i] +
                    " = ₹" + (items[i].getPrice() * quantities[i]));
        }

        System.out.println("-----------------------");
        System.out.println("Subtotal: ₹" + subtotal);
        System.out.println("Delivery Charge: ₹" + deliveryCharge);
        System.out.println("Tax (5%): ₹" + tax);
        System.out.println("Total Amount: ₹" + total);
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

        Restaurant r = new Restaurant("Food Hub", menu);
        Order order = new Order();

        r.displayMenu();

        System.out.println("\nEnter number of items you want to order:");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter item number: ");
            int choice = sc.nextInt();

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            order.addItem(menu[choice - 1], qty);
        }

        order.calculateTotal();
        order.displayOrder();
    }
}