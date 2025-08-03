package com.project.shopapp.Controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api/v1/products")
//@Validatedl

public class ProductController {
  // Hiển thị tất cả categories
  @GetMapping("") //http://localhost:8088/api/v1
  public ResponseEntity<String> getProducts(
      @RequestParam("page")  int page,
      @RequestParam("limit") int limit
  ) {
    return ResponseEntity.ok("getProducts here");
  }
  @GetMapping("/{id}")
  public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
    return ResponseEntity.ok("Product with ID: " + productId);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id){
    return ResponseEntity.status(HttpStatus.OK).body("Product delete succcess");
  }

  @PostMapping("")
// POST http://localhost:8088/v1/api/products
  public ResponseEntity<?> createProduct(
      @Valid @RequestBody ProductDTO productDTO,
      BindingResult result) {

    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }

      return ResponseEntity.ok("Product created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}



