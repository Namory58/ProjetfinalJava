package org.example.projetfinal.Controller;

import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Role;
import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Repository.RoleRepository;
import org.example.projetfinal.Security.JwtTokenManager;
import org.example.projetfinal.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth") // le enndpoint de L'api public
public class AuthController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody Users user){
        try {
            UserDetails userDetails = usersService.loadUserByUsername(user.getEmail());
            if (!userDetails.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Votre compte est bloqué.", false));
            }
            if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Identifiants incorrects ou compte non activé.", false));
            }
            Users currentUser = (Users) userDetails;
            String token = JwtTokenManager.generateToken(currentUser.getId());
            Map<String, Object> responseData = Map.of(
                    "token", token,
                    "user", Map.of(
                            "id", currentUser.getId(), // il doit être un string
                            "email", currentUser.getEmail(),
                            "username",currentUser.getUsername(),
                            "roles", currentUser.getAuthorities()
                    )
            );
            return ResponseEntity.ok(new ApiResponse<>("Connexion réussie", true, responseData));
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Utilisateur introuvable", false));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody Users user) {
        try {
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Email manquant", false));
            }
            if (user.getPassword() == null || user.getPassword().length() < 12) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Le mot de passe doit faire au moins 12 caractères", false));
            }

            if (usersService.existsByEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse<>("Cet email est déjà utilisé", false));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Erreur : Le rôle 'USER' n'existe pas en base de données."));
            user.setRoles(List.of(userRole));
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setUsername(user.getUsername());
            usersService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Inscription réussie pour " + user.getEmail(), true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Erreur serveur : " + e.getMessage(), false));
        }
    }
}
