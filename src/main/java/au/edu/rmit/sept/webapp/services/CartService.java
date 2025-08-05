package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.services.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // This annotation tells Spring to treat this class as a service (bean)
public class CartService {

    // Method to calculate the total cost of all items in the cart
    public double calculateTotal(List<Item> items) {
        double total = 0.0;

        for (Item item : items) {
            total += item.getPrice() * item.getQuantity(); // Price * Quantity for each item
        }

        return total;
    }

    // Method to add an item to the cart
public void addItem(List<Item> items, Item newItem) {
    if (newItem == null) {
        throw new IllegalArgumentException("Item cannot be null");
    }

    // Check if the item is already in the cart
    for (Item existingItem : items) {
        if (existingItem.getName().equals(newItem.getName())) {
            // Item already in cart, increase the quantity
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
            return; // Exit after updating the existing item
        }
    }

    // Item not in the cart, add it as a new item
    items.add(newItem);
}
    // Method to remove an item from the cart
public void removeItem(List<Item> items, String itemName) {
    items.removeIf(item -> item.getName().equals(itemName));
}

}
