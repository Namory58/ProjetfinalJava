package org.example.projetfinal.Controller;

import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Produits;
import org.example.projetfinal.Services.ProduitsService;
import org.example.projetfinal.dto.PageResponse; // Import du DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProduitController {

    @Autowired
    private ProduitsService produitsService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Produits>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Produits> produitsPage = produitsService.getAllProduits(page, size);
        PageResponse<Produits> paginationSimple = new PageResponse<>(
                produitsPage.getContent(),
                produitsPage.getNumber(),
                produitsPage.getSize(),
                produitsPage.getTotalElements(),
                produitsPage.getTotalPages(),
                produitsPage.isLast()
        );
        return ResponseEntity.ok(
                new ApiResponse<>("Produits récupérés avec succès", true, paginationSimple)
        );
    }
    //detail produit
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Produits>> getProduitsById(@PathVariable int id) {
        Produits produit = produitsService.getProduitsById(id);
            return ResponseEntity.ok(
                    new ApiResponse<>("Produit trouvé", true, produit)
            );
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Produits>>> getSearch(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "category", required = false, defaultValue = "") String category) {
        List<Produits> resultats = produitsService.searchProduits(name, category);
        return ResponseEntity.ok(
                new ApiResponse<>("Résultats de la recherche", true, resultats)
        );
    }

}