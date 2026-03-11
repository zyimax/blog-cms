package com.cms.controller;

import com.cms.config.JwtConfig;
import com.cms.dto.ApiResponse;
import com.cms.dto.AuthResponse;
import com.cms.dto.LoginRequest;
import com.cms.dto.RegisterRequest;
import com.cms.entity.User;
import com.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final JwtConfig jwtConfig;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        if (user != null) {
            String token = jwtConfig.generateToken(user.getUsername());
            return ApiResponse.success("登录成功", new AuthResponse(token, user));
        }
        return ApiResponse.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            user.setPassword(null);
            return ApiResponse.success("注册成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            user.setPassword(null);
            return ApiResponse.success(user);
        }
        return ApiResponse.error("用户不存在");
    }
}