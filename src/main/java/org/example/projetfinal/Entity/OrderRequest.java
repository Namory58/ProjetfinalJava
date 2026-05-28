package org.example.projetfinal.Entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class OrderRequest {
    private Integer productId;

    private Integer quantity;
}
