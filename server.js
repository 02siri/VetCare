const express = require("express");
// const stripe = require("stripe")(
//   "sk_test_51Q1RzD036DNUIvGZKGTXDRyRIumSI7nD1jx0X24jRdUo8lNsVaSE8PflqVYnpjXzv9yj57RtmV3JlxflSbCBAtSc00Ym73dkvo"
// ); // Your Secret Key
const bodyParser = require("body-parser");
const cors = require("cors"); // Import CORS package
const app = express();

// Use CORS to allow requests from your frontend (localhost:8080)
app.use(
  cors({
    origin: "http://localhost:8080",
  })
);

// Use body-parser to parse JSON requests
app.use(bodyParser.json());

// Create a checkout session
app.post("/create-checkout-session", async (req, res) => {
  const { items } = req.body;

  const lineItems = items.map((item) => ({
    price_data: {
      currency: "usd",
      product_data: {
        name: item.name,
      },
      unit_amount: item.price * 100, // Stripe expects amounts in cents
    },
    quantity: item.quantity,
  }));

  try {
    const session = await stripe.checkout.sessions.create({
      payment_method_types: ["card"],
      line_items: lineItems,
      mode: "payment",
      success_url: "http://localhost:8080/success",
      cancel_url: "http://localhost:8080/cancel",
    });

    // Send session ID back to client
    res.json({ id: session.id });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Start the server on port 4242
app.listen(4242, () => {
  console.log("Server is running on port 4242");
});
