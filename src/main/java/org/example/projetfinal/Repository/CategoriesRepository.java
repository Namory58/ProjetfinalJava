package org.example.projetfinal.Repository;

import org.example.projetfinal.Entity.Categories;
import org.example.projetfinal.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Optional<Categories> findByName(String name);
}
