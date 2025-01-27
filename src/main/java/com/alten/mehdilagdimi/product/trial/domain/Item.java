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

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }
}

