package org.example.projetfinal.Controller;

import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UsersService usersService;

    //Récuperation d'un user
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Users>> getUserById(@PathVariable int id) {
        Users user = usersService.getUserById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Utilisateur trouvé", true, user)
        );
    }
}
