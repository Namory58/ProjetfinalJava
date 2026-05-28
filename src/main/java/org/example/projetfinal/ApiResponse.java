package org.example.projetfinal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // N'affiche pas les champs nulls dans le JSON
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private T data; // Utilisation des Generics pour passer n'importe quelle entité (Product, User, etc.)
    private String timestamp;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiResponse(String message, boolean success, T data) {
        this(message, success);
        this.data = data;
    }
}
