import java.util.Scanner;

// Parent Class (Inheritance)
class Person {
    String customerName;

    Person(String customerName) {
        this.customerName = customerName;
    }
}

// FoodItem Class
class FoodItem {
    String itemName;
    double price;

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
    String name;
    FoodItem[] menu;

    Restaurant(String name, FoodItem[] menu) {
        this.name = name;
        this.menu = menu;
    }

    void displayMenu() {
        System.out.println("\nMenu of " + name);

        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". "
                    + menu[i].getItemName()
                    + " - ₹" + menu[i].getPrice());
        }
    }
}

// Order Class (Inheritance)
class Order extends Person {

    FoodItem[] items = new FoodItem[10];
    int[] quantities = new int[10];
    int count = 0;

    double subtotal = 0;
    double deliveryCharge = 0;
    double tax = 0;
    double total = 0;

    Order(String customerName) {
        super(customerName);
    }

    void addItem(FoodItem item, int quantity) {
        items[count] = item;
        quantities[count] = quantity;
        count++;
    }

    void calculateTotal() {

        subtotal = 0;

        for (int i = 0; i < count; i++) {
            subtotal += items[i].getPrice() * quantities[i];
        }

        if (subtotal > 500) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 50;
        }

        tax = subtotal * 0.05;
        total = subtotal + deliveryCharge + tax;
    }

    // Polymorphism (Method 1)
    void displayOrder() {

        System.out.println("\nOrder Summary:");
        System.out.println("-----------------------");

        for (int i = 0; i < count; i++) {
            System.out.println(
                    items[i].getItemName()
                            + " x" + quantities[i]
                            + " = ₹"
                            + (items[i].getPrice() * quantities[i]));
        }

        System.out.println("-----------------------");
        System.out.println("Subtotal: ₹" + subtotal);
        System.out.println("Delivery Charge: ₹" + deliveryCharge);
        System.out.println("Tax (5%): ₹" + tax);
        System.out.println("Total Amount: ₹" + total);
    }

    // Polymorphism (Method Overloading)
    void displayOrder(String customerName) {

        System.out.println("\nCustomer Name: " + customerName);

        displayOrder();
    }
}

// Main Class
public class FoodDeliverySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FoodItem[] menu = {
                new FoodItem("Burger", 100),
                new FoodItem("Pizza", 300),
                new FoodItem("Pasta", 200),
                new FoodItem("Cold Drink", 50)
        };

        Restaurant r = new Restaurant("Food Hub", menu);

        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine();

        Order order = new Order(customerName);

        r.displayMenu();

        int n;

        try {
            System.out.print("\nEnter number of items you want to order: ");
            n = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return;
        }

        for (int i = 0; i < n; i++) {

            try {

                System.out.print("Enter item number: ");
                int choice = sc.nextInt();

                if (choice < 1 || choice > menu.length) {
                    throw new Exception("Invalid Menu Choice!");
                }

                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                if (qty <= 0) {
                    throw new Exception("Quantity must be greater than 0!");
                }

                order.addItem(menu[choice - 1], qty);

            } catch (Exception e) {

                System.out.println(e.getMessage());
                i--;
            }
        }

        order.calculateTotal();

        order.displayOrder(customerName);

        sc.close();
    }
}