package org.example.projetfinal.Controller;

import org.apache.catalina.User;
import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Commande;
import org.example.projetfinal.Entity.Produits;
import org.example.projetfinal.Entity.Role;
import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Repository.RoleRepository;
import org.example.projetfinal.Services.CommandeService;
import org.example.projetfinal.Services.ProduitsService;
import org.example.projetfinal.Services.RoleService;
import org.example.projetfinal.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private ProduitsService produitsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommandeService commandeService;


    //getalls users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<Users>>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return ResponseEntity.ok(
                new ApiResponse<>("Liste des utilisateurs récupérée", true, users)
        );
    }

    // All rôle
    @GetMapping("/all/role")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(
                new ApiResponse<>("Liste des rôles récupéré", true, roles)
        );
    }
    //*
    @PutMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Users>> updateUser(
            @PathVariable int id,
            @RequestBody Users userDetails
    ) {
        Users updatedUser = usersService.updateUserByAdmin(id, userDetails);
        return ResponseEntity.ok(
                new ApiResponse<>("Utilisateur mis à jour avec succès", true, updatedUser)
        );
    }

    // create Produits
    @PostMapping("/produit/create")
    public ResponseEntity<ApiResponse<Produits>> saveProduits(@RequestBody Produits produit) {
        Produits produitSauvegarde = produitsService.saveProduits(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Produit créé avec succès", true, produitSauvegarde)
        );
    }
    //Update
    @PatchMapping("produit/update/{id}")
    public ResponseEntity<ApiResponse<Produits>> updateProduit(
            @PathVariable int id,
            @RequestBody Produits produit) {
        produit.setId(id);
        Produits produitMaj = produitsService.updateProduits(produit);

        return ResponseEntity.ok(
                new ApiResponse<>("Produit mis à jour avec succès", true, produitMaj)
        );
    }
    @PatchMapping("user/update/{id}")
    public ResponseEntity<ApiResponse<Users>> updateUserProfile(
            @PathVariable int id,
            @RequestBody Users userDetails) {
        userDetails.setId(id);
        Users updatedUser = usersService.updateUserByAdmin(id, userDetails);
        return ResponseEntity.ok(
                new ApiResponse<>("Utilisateur mis à jour avec succès", true, updatedUser)
        );
    }
    //delete
    @DeleteMapping("produit/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduit(@PathVariable int id) {
        produitsService.Delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Produit supprimé avec succès", true, null)
        );
    }
    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Produit supprimé avec succès", true, null)
        );
    }
    @PostMapping("/produits/import")
    public ResponseEntity<ApiResponse<List<Produits>>> importProduits(@RequestBody List<Produits> produitsList) {
        try {
            List<Produits> produitsSauvegardes = produitsService.saveAllProduits(produitsList);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(
                            "Importation réussie : " + produitsSauvegardes.size() + " produits créés.",
                            true,
                            produitsSauvegardes
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>("Erreur lors de l'import : " + e.getMessage(), false, null)
            );
        }
    }
    @GetMapping("/all-orders")
    public ResponseEntity<ApiResponse<List<Commande>>> getAllOrdersForAdmin() {
        // Cette méthode récupère la liste globale sans forcément charger les listes d'articles
        List<Commande> allCommandes = commandeService.getAllcommande();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Tableau de bord : Toutes les commandes",
                        true,
                        allCommandes
                )
        );
    }
}
