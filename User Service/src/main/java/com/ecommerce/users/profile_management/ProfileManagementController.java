package com.ecommerce.users.profile_management;

import com.ecommerce.users.user.User;
import com.ecommerce.users.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/profile")
@RequiredArgsConstructor
public class ProfileManagementController {
        private final UserRepository userRepository;

        @GetMapping("")
        public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication){
            try{
                final User user = (User) authentication.getPrincipal();

                return ResponseEntity.ok(toUserProfileResponse(user));
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("")
        public ResponseEntity<UserProfileResponse> updateUserProfile(Authentication authentication, @RequestBody UserProfileRequest req) {
            try {
                final User user = (User) authentication.getPrincipal();
                if (req.getFirstname() != null) {
                    user.setFirstname(req.getFirstname());
                }
                if (req.getLastname() != null) {
                    user.setLastname(req.getLastname());
                }
                if (req.getProfilePicUrl() != null) {
                    user.setProfilePictureUrl(req.getProfilePicUrl());
                }
                if (req.getEmail() != null) {
                    user.setEmail(req.getEmail());
                }
                if (req.getPhone() != null) {
                    user.setPhone(req.getPhone());
                }
                userRepository.save(user);
                return ResponseEntity.ok(toUserProfileResponse(user));
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        private UserProfileResponse toUserProfileResponse(User user){
            UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                    .profilePictureUrl(user.getProfilePictureUrl())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
            return userProfileResponse;
        }

}
