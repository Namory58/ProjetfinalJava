package org.example.projetfinal.Repository;

import org.example.projetfinal.Entity.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Integer> {
    List<Produits> findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCaseAndStockQuantityIsNotNull(
            String name,
            String categoryName
    );
}
