package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public record Product(

        @Id @GeneratedValue
        Long id,
        @Column(unique = true)
        String code,
        String name,
        String description,
        String image,
        String category,
        float price,
        int quantity,
        String internalReference,
        int shellId,
        InventoryStatus inventoryStatus,
        short rating,
        Long createdAt,
        Long updatedAt) {
}

