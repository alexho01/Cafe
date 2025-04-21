import org.junit.jupiter.api.*; //Brings in annotations like @Test, @BeforeEach, etc. from JUnit 5.
import static org.junit.jupiter.api.Assertions.*; // Provides testing methods like assertEquals(), assertTrue(), assertNotNull(), etc.
import java.util.*; //for using list, map ect

public class MenuTest { //all tests happen
    private Menu menu; //declares it so that all tests can use

    @BeforeEach // This method runs before every single test to make sure we have a fresh menu.
    public void setup() {
        menu = new Menu();
    }

    @Test //Passes the test if "Americano" is found.
    public void testHotCoffeeContainsAmericano() {
        List<Menu.MenuItem> hotItems = menu.menuSections.get("Hot Coffee");
        assertNotNull(hotItems);
        boolean found = hotItems.stream().anyMatch(item -> item.name.equalsIgnoreCase("Americano"));
        assertTrue(found, "Hot Coffee should include Americano");
    }

    @Test//two drinks inside "Cold Drinks". Each assertTrue will fail the test if the item isn’t found.
    public void testColdDrinksContainIcedCoffeeAndShakenEspresso() {
        List<Menu.MenuItem> coldItems = menu.menuSections.get("Cold Drinks");
        assertNotNull(coldItems);

        boolean icedCoffee = coldItems.stream().anyMatch(i -> i.name.equalsIgnoreCase("Iced Coffee"));
        boolean shakenEspresso = coldItems.stream().anyMatch(i -> i.name.equalsIgnoreCase("Iced Shaken Espresso"));

        assertTrue(icedCoffee, "Cold Drinks should include Iced Coffee");
        assertTrue(shakenEspresso, "Cold Drinks should include Iced Shaken Espresso");
    }

    @Test //Creates a fake menu item with known prices.
    //Calls getPrice("Size") and checks that the correct price is returned for each.
    public void testPriceLookupWorksCorrectly() {
        Menu.MenuItem item = new Menu.MenuItem("Test Latte", 1.0, 2.0, 3.0);
        assertEquals(1.0, item.getPrice("Small"));
        assertEquals(2.0, item.getPrice("Medium"));
        assertEquals(3.0, item.getPrice("Large"));
    }

    @Test// Checks:
    //The result is not null
    //The name returned is exactly "Caffè Latte"
    public void testGetMenuItemIgnoreCaseWorks() {
        Menu.MenuItem item = menu.getMenuItemIgnoreCase("caffè latte");
        assertNotNull(item);
        assertEquals("Caffè Latte", item.name);
    }
}
