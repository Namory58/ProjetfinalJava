package org.example.projetfinal.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    @JsonBackReference
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produits produit;

    private String productName;
    private String productImage;
    private Double price;
    private int quantity;
}