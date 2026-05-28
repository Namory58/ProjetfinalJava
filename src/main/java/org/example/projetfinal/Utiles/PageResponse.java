package org.example.projetfinal.dto; // ou ton package habituel

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;      // Ta liste de produits
    private int pageNumber;       // Page actuelle
    private int pageSize;         // Taille de la page
    private long totalElements;   // 60 dans ton cas
    private int totalPages;       // 6 dans ton cas
    private boolean last;         // Est-ce la dernière page ?
}