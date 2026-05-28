package org.example.projetfinal.Services;

import jakarta.transaction.Transactional;
import org.example.projetfinal.Entity.Categories;
import org.example.projetfinal.Entity.Produits;
import org.example.projetfinal.Repository.CategoriesRepository;
import org.example.projetfinal.Repository.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitsService {
    @Autowired
    private ProduitsRepository produitsRepository;
    @Autowired
    private CategoriesRepository categoryRepository;

    public Page<Produits> getAllProduits(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produitsRepository.findAll(pageable);
    }
    public Produits getProduitsById(int id){
        return produitsRepository.findById(id).orElse(null);
    }
    public Produits saveProduits(Produits produits){
        return produitsRepository.save(produits);
    }
    public void Delete(int id){
        produitsRepository.deleteById(id);
    }
    public List<Produits> searchProduits(String name, String categoryName) {
        String searchName = (name != null) ? name : "";
        String searchCategory = (categoryName != null) ? categoryName : "";
        return produitsRepository.findByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCaseAndStockQuantityIsNotNull(
                searchName,
                searchCategory
        );
    }

    public  Produits updateProduits(Produits produits){
        return produitsRepository.save(produits);
    }
    @Transactional
    public List<Produits> saveAllProduits(List<Produits> produits) {
        for (Produits p : produits) {
            if (p.getCategory() != null) {
                Categories cat = categoryRepository.findByName(p.getCategory().getName())
                        .orElseGet(() -> {
                            Categories newCat = new Categories();
                            newCat.setName(p.getCategory().getName());
                            return categoryRepository.save(p.getCategory());
                        });
                p.setCategory(cat);
            }
        }
        return produitsRepository.saveAll(produits);
    }

}
