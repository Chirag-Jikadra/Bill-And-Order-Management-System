package com.example.Bill.Generation.System.Controller;

import com.example.Bill.Generation.System.Repository.ProductRepo;
import com.example.Bill.Generation.System.model.OrderRequest;
import com.example.Bill.Generation.System.model.Product;
import com.example.Bill.Generation.System.service.EmailService;
import com.example.Bill.Generation.System.service.OrderService;
import com.example.Bill.Generation.System.service.ProductService;
import jakarta.mail.MessagingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductRepo productRepo;

    @PostMapping("/product")
    public ResponseEntity<String> productSave(@RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.ok("✅ Product saved successfully!");
    }

    @PostMapping("/order")
    public ResponseEntity<String> order(@RequestBody OrderRequest orderRequest) {
        if (orderRequest.getCid() <= 0) {
            return ResponseEntity.badRequest().body("❌ Invalid customer ID: " + orderRequest.getCid());
        }
        try {
            orderService.order(orderRequest);
            return ResponseEntity.ok("✅ Order placed successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Failed to place order: " + e.getMessage());
        }
    }

    @GetMapping("/send-product-report")
    public ResponseEntity<String> sendProductReport() {
        try {
            List<Product> products = productRepo.findAll();
            String filePath = generateProductCsv(products);

            boolean thresholdAlert = products.stream()
                    .anyMatch(p -> p.getQuntity() <= p.getThreshold());

            emailService.sendProductReport(filePath, thresholdAlert);
            return ResponseEntity.ok("✅ Product report emailed successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Failed to send product report: " + e.getMessage());
        }
    }

    private String generateProductCsv(List<Product> products) throws IOException {
        String filePath = "Product_Report.csv";
        try (FileWriter writer = new FileWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("Product Id", "Product Name", "Price", "Stock Left", "GST", "Threshold"))) {

            for (Product product : products) {
                csvPrinter.printRecord(
                        product.getPid(),
                        product.getProductName(),
                        product.getPrice(),
                        product.getQuntity(),
                        product.getGst(),
                        product.getThreshold()
                );
            }
            csvPrinter.flush();
        }
        return filePath;
    }
}
