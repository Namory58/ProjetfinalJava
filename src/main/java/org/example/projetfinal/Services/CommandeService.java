package org.example.projetfinal.Services;

import jakarta.transaction.Transactional;
import org.example.projetfinal.Entity.Commande;
import org.example.projetfinal.Entity.OrderItem;
import org.example.projetfinal.Entity.Produits;
import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Repository.CommandeRepository;
import org.example.projetfinal.Repository.ProduitsRepository;
import org.example.projetfinal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Récupérer les commandes d’un utilisateur
    public List<Commande> getAllcommande(int id) {
        return commandeRepository.findByUserId(id);
    }
    public List<Commande> getAllcommande() {
        return commandeRepository.findAll();
    }
    @Transactional
    public Commande createCommande(List<OrderItem> produitsItems, int userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Commande commande = new Commande();
        commande.setUser(user);
        commande.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalCalcul = 0;
        int totalQuantite = 0;
        for (OrderItem item : produitsItems) {
            if (item.getProduit() == null || item.getProduit().getId() == null) {
                throw new RuntimeException("Produit invalide dans la commande");
            }
            Produits produit = produitsRepository.findById(item.getProduit().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Produit ID " + item.getProduit().getId() + " non trouvé"));
            if (produit.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Stock insuffisant pour : " + produit.getName());
            }
            produit.setStockQuantity(produit.getStockQuantity() - item.getQuantity());
            produitsRepository.save(produit);
            OrderItem orderItem = new OrderItem();
            orderItem.setCommande(commande);
            orderItem.setProduit(produit);
            orderItem.setProductName(produit.getName());
            orderItem.setProductImage(produit.getLienImage());
            orderItem.setPrice(produit.getPrice());
            orderItem.setQuantity(item.getQuantity());
            totalCalcul += produit.getPrice() * item.getQuantity();
            totalQuantite += item.getQuantity();
            orderItems.add(orderItem);
        }
        commande.setOrderItems(orderItems);
        commande.setTotalAmount(totalCalcul);
        commande.setQuantite(totalQuantite);
        commande.setUserId(user.getId());
        return commandeRepository.save(commande);
    }

}