package org.example.projetfinal.Controller;

import lombok.Data;

@Data
public class OrderRequest {
    private Integer productId;
    private Integer quantity;
}
