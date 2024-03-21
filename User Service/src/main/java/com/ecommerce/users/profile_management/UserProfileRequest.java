package com.ecommerce.users.profile_management;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {
    @Nullable
    private String email;
    @Nullable
    private String firstname;
    @Nullable
    private String lastname;
    @Nullable
    private String phone;
    @Nullable
    private String profilePicUrl;
}
