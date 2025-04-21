import java.util.*; //to include classes like scanner ect
import java.nio.file.*; //makes it so i can make txt like recipt
import java.time.LocalDateTime; //makes it so that the txt can show the time

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //to initialize scanner for user input
        Menu cafeMenu = new Menu(); //to load the menu
        List<OrderItem> orderList = new ArrayList<>(); //for the person order

        List<String> categories = new ArrayList<>(cafeMenu.menuSections.keySet());//get catagory like coffee
        categories.add("Finish & Checkout"); //always show this at end of order

        boolean running = true; //my case loop
        while (running) {
            System.out.println("\n=== Main Menu ===");
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, categories.get(i)); //to show the catagories
            }
            System.out.print("Select a category: ");
            int catChoice = readMenuInput(scanner, 1, categories.size(), orderList);

            String category = categories.get(catChoice - 1);
            if (category.equals("Finish & Checkout")) {
                break;
            }

            List<Menu.MenuItem> items = cafeMenu.menuSections.get(category); //list of menu based on catagory
            boolean inCategory = true;
            while (inCategory) {
                System.out.println("\n--- " + category + " ---");
                for (int i = 0; i < items.size(); i++) {
                    System.out.printf("%d. %s\n", i + 1, items.get(i));
                }
                System.out.println("0. Back to Main Menu");
                System.out.print("Choose an item: ");
                int itemChoice = readMenuInput(scanner, 0, items.size(), orderList);
                if (itemChoice == 0) {
                    inCategory = false;
                    break;
                }

                Menu.MenuItem picked = items.get(itemChoice - 1); //picks selected item , for smal med large

                System.out.print("Enter size (Small / Medium / Large): ");
                String size = readStringInput(scanner, orderList);
                size = capitalize(size);
                if (!picked.sizes.containsKey(size)) {
                    System.out.println("Invalid size. Try that item again.");
                    continue;
                }

                System.out.print("Enter quantity: ");
                String qtyInput = readStringInput(scanner, orderList);
                int qty = 1;
                try {
                    qty = Integer.parseInt(qtyInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number; defaulting to 1.");
                }

                orderList.add(new OrderItem(picked, size, qty));
                System.out.printf("%dx %s %s added to your order.\n", qty, size, picked.name);
            }
        }

        printReceipt(orderList);
    }

    // Handles numeric menu selection or exits on "done"
    private static int readMenuInput(Scanner scanner, int min, int max, List<OrderItem> orderList) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) { //show recipet
                printReceipt(orderList);
                System.exit(0);
            }
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.printf("Invalid input. Please enter a number between %d and %d: ", min, max);
        }
    }

    // Handles string input and exits on "done"
    private static String readStringInput(Scanner scanner, List<OrderItem> orderList) {
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("done")) {//show reciept
            printReceipt(orderList);
            System.exit(0);
        }
        return input;
    }

  //makes sure its okay for cap words  
    private static String capitalize(String str) {
        if (str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    //what they ordered
    static class OrderItem {
        final Menu.MenuItem menuItem;
        final String size;
        final int quantity;

        OrderItem(Menu.MenuItem menuItem, String size, int quantity) {
            this.menuItem = menuItem;
            this.size = size;
            this.quantity = quantity;
        }
    }

    //prints the recipet
    private static void printReceipt(List<OrderItem> orderList) {
        StringBuilder receipt = new StringBuilder(); //initlizes
        receipt.append("\n======= RECEIPT =======\n");
        double subtotal = 0;

        for (OrderItem item : orderList) {
            double price = item.menuItem.getPrice(item.size);
            double lineTotal = price * item.quantity;
            subtotal += lineTotal; //gets cost
            receipt.append(String.format(
                "%-2dx %-20s (%s) @ $%.2f = $%.2f\n",
                item.quantity, item.menuItem.name, item.size, price, lineTotal
            ));
        }

        //gets total cost
        double tax = subtotal * 0.13;
        double total = subtotal + tax;
        receipt.append(String.format("\nSubtotal: $%.2f\n", subtotal));
        receipt.append(String.format("HST (13%%): $%.2f\n", tax));
        receipt.append(String.format("Total: $%.2f\n", total));
        receipt.append("========================\n");
        receipt.append("Thanks for visiting the café!\n"); 

        // Print to console
        System.out.println(receipt);

        // Save to file with timestamp
        String timestamp = LocalDateTime.now()
            .toString()
            .replace("T", "_")
            .replace(":", "-")
            .substring(0, 16); // yyyy-MM-dd_HH-mm
        String filename = "receipt-" + timestamp + ".txt";


        //read/write file so it makes they txt file.
        try {
            Files.write(Paths.get(filename), receipt.toString().getBytes());
            System.out.println("Receipt saved to '" + filename + "'.");
        } catch (Exception e) {
            System.out.println("Could not save receipt: " + e.getMessage());
        }
    }
}
