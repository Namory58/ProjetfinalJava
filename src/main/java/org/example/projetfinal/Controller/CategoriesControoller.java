package org.example.projetfinal.Controller;

import org.example.projetfinal.ApiResponse;
import org.example.projetfinal.Entity.Categories;
import org.example.projetfinal.Services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesControoller {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCategories() {
        List<Categories> categories = categoriesService.getAllCategories();
        ApiResponse<Object> response = new ApiResponse<>(
                "Catégories récupérées avec succès",
                true,
                categories
        );
        return ResponseEntity.ok(response);
    }
}
