package com.project.shopapp.Controllers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

  @PostMapping("/register")
  public ResponseEntity<?> creatUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
        return ResponseEntity.badRequest().body("Password not match");
      }
      return ResponseEntity.ok("Regester Succesful");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    // try catch dung de bat loi
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userlogin) {
    return ResponseEntity.ok("some token");
    // kiem tra thong tin va sinh token
    // tra ve token trong respone
  }
}


