package au.edu.rmit.sept.webapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import au.edu.rmit.sept.webapp.services.Item;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CartController {

    @PostConstruct
    public void init() {
        // Initialize Stripe with your SECRET key
        Stripe.apiKey = "sk_test_51Q1RzD036DNUIvGZKGTXDRyRIumSI7nD1jx0X24jRdUo8lNsVaSE8PflqVYnpjXzv9yj57RtmV3JlxflSbCBAtSc00Ym73dkvo";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        // Get the cart from the session
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            model.addAttribute("cartEmpty", true);
        } else {
            model.addAttribute("cartEmpty", false);
            model.addAttribute("cartItems", cart); // Pass the cart items to the view
        }

        return "cart";  // This should point to the corresponding Thymeleaf HTML file (cart.html)
    }

    // API to retrieve cart items dynamically via AJAX
    @GetMapping("/api/cart-items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        if (cart == null) {
            cart = List.of();  // Return an empty list if the cart is null
        }
        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cart);
        return ResponseEntity.ok(response);
    }

    // API to remove an item from the cart dynamically via AJAX
    @DeleteMapping("/api/cart/remove-item/{itemName}")
    public ResponseEntity<Void> removeFromCart(@PathVariable String itemName, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getName().equals(itemName));
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok().build();
    }

        @PostMapping("/api/cart/update-quantity/{itemName}")
    public ResponseEntity<Void> updateItemQuantity(
            @PathVariable String itemName,
            @RequestParam("quantity") int quantity,
            HttpSession session) {
        
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        
        if (cart != null) {
            for (Item item : cart) {
                if (item.getName().equals(itemName)) {
                    item.setQuantity(quantity); // Update the quantity of the item
                    break; // Exit loop once item is found and updated
                }
            }
            session.setAttribute("cart", cart); // Save the updated cart in session
        }

        return ResponseEntity.ok().build();
    }

@PostMapping("/cart/update-quantity/{itemName}")
public String updateQuantity(@PathVariable String itemName, @RequestParam("quantity") int quantity, HttpSession session) {
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart != null) {
        for (Item item : cart) {
            if (item.getName().equals(itemName)) {
                item.setQuantity(quantity);
                break;
            }
        }
        session.setAttribute("cart", cart);
    }
    return "redirect:/cart";  // Redirect back to cart page
}


    // API to create Stripe Checkout session
    @PostMapping("/api/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return ResponseEntity.badRequest().build(); // If cart is empty, no session is created
        }

        // Map your cart items to Stripe line items
        List<SessionCreateParams.LineItem> lineItems = cart.stream().map(item -> {
            return SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(item.getName())
                            .build())
                        .setUnitAmount((long) (item.getPrice() * 100))  // Stripe expects price in cents
                        .build())
                .setQuantity((long) item.getQuantity())
                .build();
        }).collect(Collectors.toList());

        try {
            // Create Stripe session
            SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8080/success")
            .setCancelUrl("http://localhost:8080/cancel")
            .addAllLineItem(lineItems)
            .build();


            Session stripeSession = Session.create(params);

            Map<String, String> response = new HashMap<>();
            response.put("id", stripeSession.getId());

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
