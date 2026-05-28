package org.example.projetfinal.Repository;

import org.example.projetfinal.Entity.Categories;
import org.example.projetfinal.Entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByUserId(@Param("userId") Integer userId);
}
