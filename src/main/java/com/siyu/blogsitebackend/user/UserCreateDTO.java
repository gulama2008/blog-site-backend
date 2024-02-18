package com.siyu.blogsitebackend.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank
    private String username;
    @NotBlank
     private String password;
}
