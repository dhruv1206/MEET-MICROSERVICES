package com.ecommerce.users.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest{

    @Email(message = "Invalid email id!")
    private String email;
    @NotNull
    @Size(min=8,max = 32, message = "Username should contain between 8 to 32 characters.")
    private String username;
    @NotNull
    @Size(min=8, max = 32, message = "Password should contain between 8 to 32 characters.")
    private String password;
    @Size(min = 10, max = 10, message = "Phone number should contain exactly 10 digits")
    private String phone;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
}
