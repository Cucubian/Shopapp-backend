package com.project.shopapp.Controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/products")
//@Validatedl

public class ProductController {

  // Hiển thị tất cả categories
  @GetMapping("") //http://localhost:8088/api/v1
  public ResponseEntity<String> getProducts(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
  ) {
    return ResponseEntity.ok("getProducts here");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
    return ResponseEntity.ok("Product with ID: " + productId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body("Product delete succcess");
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
// POST http://localhost:8088/v1/api/products
  public ResponseEntity<?> createProduct(
      @Valid @ModelAttribute ProductDTO productDTO,
      //@RequestPart("file") MultipartFile file,
      BindingResult result) {

    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      MultipartFile file = productDTO.getFile();
      if (file != null) {
        // Kiểm tra kích thước file (giới hạn tối đa 10MB)
        if (file.getSize() > 10 * 1024 * 1024) {
          return ResponseEntity
              .status(HttpStatus.PAYLOAD_TOO_LARGE)
              .body("File is too large! Maximum size is 10MB");
        }

        // Kiểm tra định dạng file (phải là ảnh, ví dụ: image/png, image/jpeg)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
          return ResponseEntity
              .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
              .body("File must be an image");
        }
        String filename = storeFile(file);
      }
      return ResponseEntity.ok("Product created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private String storeFile(MultipartFile file) throws IOException {
    // Lấy tên file gốc và làm sạch đường dẫn
    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    // Tạo tên file duy nhất bằng cách thêm UUID phía trước
    String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

    // Tạo đường dẫn thư mục uploads
    Path uploadDir = Paths.get("uploads");

    // Nếu thư mục chưa tồn tại thì tạo mới
    if (!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }

    // Tạo đường dẫn đầy đủ đến file đích
    Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

    // Sao chép nội dung file vào thư mục đích
    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

    // Trả về tên file duy nhất
    return uniqueFilename;
  }
}


