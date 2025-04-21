import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class OrderLogicTest {

    @Test //creates caffe latte with prices, simulates ordering 2 caffe latte, the total price should be 11.865
    public void testSubtotalTaxTotal() {
        Menu.MenuItem latte = new Menu.MenuItem("Caffè Latte", 4.95, 5.25, 5.45);
        List<Main.OrderItem> orderList = new ArrayList<>();
        orderList.add(new Main.OrderItem(latte, "Medium", 2)); // 5.25 * 2 = 10.50

        double subtotal = orderList.stream()
            .mapToDouble(i -> i.menuItem.getPrice(i.size) * i.quantity)
            .sum();

        double tax = subtotal * 0.13;
        double total = subtotal + tax;

        assertEquals(10.50, subtotal, 0.001);
        assertEquals(1.365, tax, 0.001);
        assertEquals(11.865, total, 0.001);
    }

    @Test //creates order item using large, and 4 of them, makes sure saved correct size, quantity, and corrrect item name
    public void testOrderItemHoldsCorrectData() {
        Menu.MenuItem item = new Menu.MenuItem("Test Item", 1.0, 2.0, 3.0);
        Main.OrderItem order = new Main.OrderItem(item, "Large", 4);
        assertEquals("Large", order.size);
        assertEquals(4, order.quantity);
        assertEquals("Test Item", order.menuItem.name);
    }
}
