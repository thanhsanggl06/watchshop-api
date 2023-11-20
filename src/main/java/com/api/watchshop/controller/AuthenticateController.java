package com.api.watchshop.controller;

import com.api.watchshop.dto.LoginRequest;
import com.api.watchshop.dto.LoginResponse;
import com.api.watchshop.exception.ErrorDetails;
import com.api.watchshop.security.CustomUserDetails;
import com.api.watchshop.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins ={ "http://localhost:4200", "http://localhost:4201"})
@AllArgsConstructor
public class AuthenticateController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return  ResponseEntity.ok(new LoginResponse(jwt, loginRequest.getUsername()));
        } catch(BadCredentialsException ex) {
            // Xử lý khi tên đăng nhập hoặc mật khẩu không đúng
            ErrorDetails err = new ErrorDetails(LocalDateTime.now(),"Invalid username or password","/authenticate/login","INVALID_USERNAME_OR_PASSWORD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }catch(ExpiredJwtException ex){
            // Xử lý khi token đã hết hạn
            ErrorDetails err = new ErrorDetails(LocalDateTime.now(),"Token has expired","/authenticate/login","TOKEN_HAS_EXPIRED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }
        catch (LockedException ex) {
            // Xử lý khi tài khoản bị khóa
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your account has been locked");
        } catch (DisabledException ex) {
            // Xử lý khi tài khoản bị vô hiệu hóa
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your account is disabled");
        } catch (Exception ex) {
            // Xử lý các trường hợp lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }

    }
}
