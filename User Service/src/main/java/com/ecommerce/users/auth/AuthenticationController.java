package com.ecommerce.users.auth;

import com.ecommerce.users.user.User;
import com.ecommerce.users.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest req
    ) throws MethodArgumentNotValidException
    {
        try{
            final Optional<User> user = userRepository.findUserByUsername(req.getUsername());
            if(user.isPresent()){
                HashMap<String, String> error = new HashMap<>();
                error.put("error", "User already exists with this username!");
                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
            return ResponseEntity.ok(authenticationService.register(req));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
           @RequestBody AuthenticationRequest req
    ) {
        try{
            return ResponseEntity.ok(authenticationService.authenticate(req));
        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
