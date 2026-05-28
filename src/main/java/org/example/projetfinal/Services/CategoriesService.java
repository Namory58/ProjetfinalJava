package org.example.projetfinal.Services;

import org.example.projetfinal.Entity.Categories;
import org.example.projetfinal.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService{
    @Autowired
    private CategoriesRepository categoriesRepository;

   public   List<Categories> getAllCategories(){
        return categoriesRepository.findAll();
    };
   public Categories getCategoryByName(String nameC){
       return categoriesRepository.findByName(nameC).orElse(null);
   }
}
