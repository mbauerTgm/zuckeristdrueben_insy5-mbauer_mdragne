package com.mbauer_mdragne.vue_crud;

import com.mbauer_mdragne.vue_crud.Entities.Users;
import com.mbauer_mdragne.vue_crud.Entities.Role;
import com.mbauer_mdragne.vue_crud.Repositories.RoleRepository;
import com.mbauer_mdragne.vue_crud.Services.UsersService;
import com.mbauer_mdragne.vue_crud.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersService UsersService;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsersService UsersService, RoleRepository roleRepository, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.UsersService = UsersService;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== SignIn =====
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest req) {
        if (req == null || req.UsersID == null || req.password == null) {
            return problem(HttpStatus.BAD_REQUEST, "Missing credentials");
        }

        Optional<Users> UsersOpt = UsersService.getUserByUserID(req.UsersID);
        if (UsersOpt.isEmpty()) return problem(HttpStatus.UNAUTHORIZED, "Invalid Usersname or password");

        Users Users = UsersOpt.get();
        if (!passwordEncoder.matches(req.password, Users.getPassword())) {
            return problem(HttpStatus.UNAUTHORIZED, "Invalid Usersname or password");
        }

        String token = jwtUtil.generateToken(Users.getId().toString(), List.of(Users.getRole().getName()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "login_success");
        body.put("token", token);
        body.put("UsersId", Users.getId());
        body.put("UsersID", Users.getUserID());
        body.put("role", Users.getRole().getName());

        return ResponseEntity.ok(body);
    }

    // ===== Register (Admin only) =====
    @PostMapping("/admin/register")
    public ResponseEntity<?> register(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody RegisterRequest req) {

        if (!isAdmin(stripBearer(authHeader))) {
            return problem(HttpStatus.FORBIDDEN, "admin_required");
        }

        boolean exists = UsersService.getAllUsers().stream()
                .anyMatch(u -> Objects.equals(u.getUserID(), req.UsersID));
        if (exists) return problem(HttpStatus.CONFLICT, "UsersID_already_exists");

        Role role = roleRepository.findByName(req.roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + req.roleName));

        Users u = new Users();
        u.setUserID(req.UsersID);
        u.setPassword(passwordEncoder.encode(req.password));
        u.setRole(role);
        u.setCreatedAt(LocalDateTime.now());

        Users saved = UsersService.updateUser(u);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Users_created");
        body.put("id", saved.getId());
        body.put("UsersID", saved.getUserID());
        body.put("role", saved.getRole().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // ===== Helpers =====
    private String stripBearer(String authHeader) {
        if (authHeader == null) return null;
        if (authHeader.toLowerCase(Locale.ROOT).startsWith("bearer ")) {
            return authHeader.substring(7).trim();
        }
        return authHeader.trim();
    }

    private boolean isAdmin(String token) {
        try {
            if (token == null) return false;
            Jws<Claims> claims = jwtUtil.validateToken(token);
            List<String> roles = (List<String>) claims.getBody().get("roles");
            return roles != null && roles.contains("Admin");
        } catch (Exception e) {
            return false;
        }
    }

    private ResponseEntity<Map<String, Object>> problem(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

    // ===== DTOs =====
    public static class LoginRequest {
        public String UsersID;
        public String password;
    }

    public static class RegisterRequest {
        public String UsersID;
        public String password;
        public String roleName;
    }
}
