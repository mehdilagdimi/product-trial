package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

@Entity
public class Item {
        @Id
        @GeneratedValue
        Long id;
        @ManyToOne
        Product product;
        int quantity;

        public Long id() {
                return id;
        }

        public Item setId(Long id) {
                this.id = id;
                return this;
        }

        public Product product() {
                return product;
        }

        public Item setProduct(Product product) {
                this.product = product;
                return this;
        }

        public int quantity() {
                return quantity;
        }

        public Item setQuantity(int quantity) {
                this.quantity = quantity;
                return this;
        }
}

