package au.edu.rmit.sept.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.services.CartService;
import au.edu.rmit.sept.webapp.services.Item;
import jakarta.servlet.http.HttpSession;

@Controller
public class PrescriptionController {

  @Autowired  // Injecting CartService
    private CartService cartService;

    @GetMapping("/prescription")
    public String getPrescription(Model model) {
        // Create a list of sample products
        List<Prescription> products = new ArrayList<>();
        products.add(new Prescription("1", "Dog", "Apoquel (Oclacitinib)", "5.4mg", "Taylor Swift", "2024-09-16"));
        products.add(new Prescription("2", "Dog/Cat", "Metacam (Meloxicam)", "Oral Suspension 180ml", "Post Malone", "2024-09-25"));
        products.add(new Prescription("3", "Dog/Cat", "Clavamox (Amoxicillin)", "15ml", "Keshi", "2024-09-04"));
        products.add(new Prescription("4", "All", "Baytril (Enrofloxacin)", "20ml", "Maroon5", "2024-09-09"));
        products.add(new Prescription("5", "Dog/Cat", "Prozac (Fluoxetine)", "20mg Tablets", "Justin Bieber", "2024-09-15"));
        products.add(new Prescription("6", "Dog/Cat", "Cerenia (Maropitant)", "10mg Tablets", "Rosie Daring", "2024-09-16"));

        // Add the products to the model
        model.addAttribute("products", products);

        return "prescription";  // Corresponding HTML page
    }

     // Method to handle adding items to the cart
    @PostMapping("/cart")
public String addToCart(@RequestParam("name") String name,
                        @RequestParam("price") double price,
                        HttpSession session) {
    // Create a new item
    Item item = new Item(name, price, 1);

    // Get or create the cart
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart == null) {
        cart = new ArrayList<>();
        session.setAttribute("cart", cart);
    }

    // Add the item to the cart using CartService
    cartService.addItem(cart, item);

    // Redirect back to the prescription page
    return "redirect:/prescription";
}

    
}
