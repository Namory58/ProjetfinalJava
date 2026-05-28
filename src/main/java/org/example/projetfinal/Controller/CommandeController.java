package org.example.projetfinal.Controller;

import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Commande;
import org.example.projetfinal.Entity.OrderItem;
import org.example.projetfinal.Services.CommandeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Object>> createOrder(
            @RequestBody List<OrderItem>  items,
            @PathVariable int userId
    ) {
        Commande commande = commandeService.createCommande(items, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        "Commande créée avec succès",
                        true,
                        commande
                ));
    }
    @GetMapping("/my-orders/{id}")
    public ResponseEntity<ApiResponse<List<Commande>>> getAllOrders(
            @PathVariable int id
    ) {
        List<Commande> commandes = commandeService.getAllcommande(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Liste des commandes récupérée",
                        true,
                        commandes
                )
        );
    }
}