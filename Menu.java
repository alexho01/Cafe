import java.util.*;

public class Menu {
    public static class MenuItem {
        String name;
        Map<String, Double> sizes;

        public MenuItem(String name, double small, double medium, double large) {
            this.name = name;
            sizes = new HashMap<>();
            sizes.put("Small", small);
            sizes.put("Medium", medium);
            sizes.put("Large", large);
        }

        public double getPrice(String size) {
            return sizes.getOrDefault(size, -1.0);
        }

        public String toString() {
            return name + " - Small: $" + sizes.get("Small") +
                   ", Medium: $" + sizes.get("Medium") +
                   ", Large: $" + sizes.get("Large");
        }
    }

    public Map<String, List<MenuItem>> menuSections;

    public Menu() {
        menuSections = new LinkedHashMap<>();
        buildMenu();
    }

    private void buildMenu() {
        addSection("Hot Coffee", new MenuItem[]{
            new MenuItem("Featured Dark Roast", 2.45, 2.75, 2.95),
            new MenuItem("Caffè Misto", 3.25, 3.55, 3.75),
            new MenuItem("Blonde Roast - Sunsera", 2.45, 2.75, 2.95),
            new MenuItem("Caffè Latte", 4.95, 5.25, 5.45),
            new MenuItem("Americano", 4.45, 4.75, 4.95) // ✅ Added here
        });

        addSection("Cold Drinks", new MenuItem[]{
            new MenuItem("Cold Brew", 4.55, 4.85, 5.05),
            new MenuItem("Vanilla Sweet Cream Cold Brew", 4.95, 5.25, 5.45),
            new MenuItem("Salted Caramel Cream Cold Brew", 5.25, 5.45, 5.65),
            new MenuItem("Iced Coffee", 4.55, 4.85, 5.05),           // ✅ Added here
            new MenuItem("Iced Shaken Espresso", 4.35, 4.65, 4.85)   // ✅ Added here
        });

        addSection("Iced Tea", new MenuItem[]{
            new MenuItem("Iced Matcha Tea Latte", 5.29, 5.59, 5.79),
            new MenuItem("Iced Cherry Chai Latte", 6.45, 6.75, 6.95),
            new MenuItem("Iced Chai Latte", 5.65, 5.95, 6.15),
            new MenuItem("Iced Black Tea", 3.45, 3.75, 3.95),
            new MenuItem("Iced Green Tea", 3.45, 3.75, 3.96),
            new MenuItem("Iced Peach Green Tea", 3.45, 3.75, 3.96)
        });

        addSection("Bakery", new MenuItem[]{
            new MenuItem("Ham & Swiss Croissant", 4.45, 4.45, 4.45),
            new MenuItem("Cheese Danish", 3.65, 3.65, 3.65),
            new MenuItem("Butter Croissant", 3.75, 3.75, 3.75),
            new MenuItem("Chocolate Croissant", 3.95, 3.95, 3.95)
        });
    }

    private void addSection(String sectionName, MenuItem[] items) {
        menuSections.put(sectionName, Arrays.asList(items));
    }

    public void displayMenu() {
        for (String section : menuSections.keySet()) {
            System.out.println("\n=== " + section + " ===");
            for (MenuItem item : menuSections.get(section)) {
                System.out.println(item);
            }
        }
    }

    // Case insensitive search for any item
    public MenuItem getMenuItemIgnoreCase(String name) {
        for (List<MenuItem> items : menuSections.values()) {
            for (MenuItem item : items) {
                if (item.name.equalsIgnoreCase(name)) return item;
            }
        }
        return null;
    }
}
