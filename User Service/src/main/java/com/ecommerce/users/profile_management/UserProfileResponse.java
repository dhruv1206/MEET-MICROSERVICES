package com.ecommerce.users.profile_management;


import com.ecommerce.users.user.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    @Nullable
    private String profilePictureUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
}
