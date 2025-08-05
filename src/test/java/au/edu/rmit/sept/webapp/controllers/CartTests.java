package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.services.Item;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTests {

    private CartController cartController;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartController = new CartController();
    }

    @Test
    void testShowCartWhenCartIsEmpty() {
        // Arrange
        when(session.getAttribute("cart")).thenReturn(null);

        // Act
        String viewName = cartController.showCart(model, session);

        // Assert
        verify(model).addAttribute("cartEmpty", true);
        assertEquals("cart", viewName);
    }

    @Test
    void testShowCartWhenCartHasItems() {
        // Arrange
        List<Item> cart = new ArrayList<>();
        cart.add(new Item("Product A", 100.0, 1));  // Fix: pass correct order of arguments (name, price, quantity)
        when(session.getAttribute("cart")).thenReturn(cart);

        // Act
        String viewName = cartController.showCart(model, session);

        // Assert
        verify(model).addAttribute("cartEmpty", false);
        verify(model).addAttribute("cartItems", cart);
        assertEquals("cart", viewName);
    }

    @Test
    void testGetCartItems() {
        // Arrange
        List<Item> cart = new ArrayList<>();
        cart.add(new Item("Product A", 100.0, 1));  // Fix: pass correct order of arguments (name, price, quantity)
        when(session.getAttribute("cart")).thenReturn(cart);

        // Act
        ResponseEntity<Map<String, Object>> response = cartController.getCartItems(session);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, ((List<?>) response.getBody().get("cartItems")).size());
    }

    @Test
    void testRemoveFromCart() {
        // Arrange
        List<Item> cart = new ArrayList<>();
        cart.add(new Item("Product A", 100.0, 1));  // Fix: pass correct order of arguments (name, price, quantity)
        cart.add(new Item("Product B", 50.0, 2));   // Fix: pass correct order of arguments (name, price, quantity)
        when(session.getAttribute("cart")).thenReturn(cart);

        // Act
        ResponseEntity<Void> response = cartController.removeFromCart("Product A", session);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(session).setAttribute(eq("cart"), any(List.class));
        assertEquals(1, cart.size());
        assertEquals("Product B", cart.get(0).getName());
    }

    @Test
    void testUpdateItemQuantity() {
        // Arrange
        List<Item> cart = new ArrayList<>();
        cart.add(new Item("Product A", 100.0, 1));  // Fix: pass correct order of arguments (name, price, quantity)
        when(session.getAttribute("cart")).thenReturn(cart);

        // Act
        ResponseEntity<Void> response = cartController.updateItemQuantity("Product A", 3, session);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(3, cart.get(0).getQuantity());
    }

}
