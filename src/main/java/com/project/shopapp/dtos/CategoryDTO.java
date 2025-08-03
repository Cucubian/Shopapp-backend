package com.project.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // tostring
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDTO {

  @NotEmpty(message = "khong duoc de trong ten -  Category's name can not be empty ")
  private String name;
}
