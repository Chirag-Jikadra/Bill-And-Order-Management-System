package com.example.Bill.Generation.System.service;

import com.example.Bill.Generation.System.Repository.CustomerRepo;
import com.example.Bill.Generation.System.Repository.OrderRepo;
import com.example.Bill.Generation.System.Repository.ProductRepo;
import com.example.Bill.Generation.System.model.Customer;
import com.example.Bill.Generation.System.model.OrderRequest;
import com.example.Bill.Generation.System.model.Orders;
import com.example.Bill.Generation.System.model.Product;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    private final Random random = new Random();

    @Transactional
    public void order(OrderRequest orderRequest) throws IOException, MessagingException {
        Customer customer = customerRepo.findById(orderRequest.getCid())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + orderRequest.getCid()));

        double grandTotal = 0.0;

        for (Map.Entry<Integer, Integer> entry : orderRequest.getHashMap().entrySet()) {
            Product product = productRepo.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + entry.getKey()));

            int qty = entry.getValue();
            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0 for product: " + product.getProductName());
            }

            int price = product.getPrice();
            double gst = (price * product.getGst() / 100.0);
            double total = (price + gst) * qty;

            String status = random.nextInt(5) < 4 ? "PAID" : "UNPAID";

            if ("PAID".equals(status)) {
                if (product.getQuntity() < qty) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
                }
                product.setQuntity(product.getQuntity() - qty);
                productRepo.save(product);
            }

            Orders order = new Orders();
            order.setCustomer(customer);
            order.setProduct(product);
            order.setQuantity(qty);
            order.setGstRate(gst);
            order.setPrice(price);
            order.setTotalAmount(total);
            orderRepo.save(order);

            grandTotal += total;

            String message;
            if ("PAID".equals(status)) {
                message = "Hi " + customer.getCustomerName() +
                        ", your payment of Rs." + total +
                        " for product " + product.getProductName() +
                        " is successful. Thank you!";
            } else {
                message = "Hi " + customer.getCustomerName() +
                        ", your order for product " + product.getProductName() +
                        " is placed but payment is pending.";
            }

            notificationService.sendSms(customer.getModNo(), message);
            notificationService.sendWhatsApp(customer.getModNo(), message);
        }
    }
}
