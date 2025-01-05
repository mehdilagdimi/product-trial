package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
public class Product {

        public Product(){}
        public Product(Long id, String code, String name, String description, String image, String category, float price, int quantity, String internalReference, int shellId, InventoryStatus inventoryStatus, short rating, Long createdAt, Long updatedAt) {
                this.id = id;
                this.code = code;
                this.name = name;
                this.description = description;
                this.image = image;
                this.category = category;
                this.price = price;
                this.quantity = quantity;
                this.internalReference = internalReference;
                this.shellId = shellId;
                this.inventoryStatus = inventoryStatus;
                this.rating = rating;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

        @Id @GeneratedValue
        Long id;
        @Column(unique = true)
        String code;
        String name;
        String description;
        String image;
        String category;
        float price;
        int quantity;
        String internalReference;
        int shellId;
        InventoryStatus inventoryStatus;
        short rating;
        Long createdAt;
        Long updatedAt;

        @PrePersist
        public void prePersist(){
              this.createdAt = Date.from(Instant.now()).getTime();
        }

        @PreUpdate
        public void preUpdate(){
                this.updatedAt = Date.from(Instant.now()).getTime();
        }

        public Long id() {
                return id;
        }

        public Product setId(Long id) {
                this.id = id;
                return this;
        }

        public String code() {
                return code;
        }

        public Product setCode(String code) {
                this.code = code;
                return this;
        }

        public String name() {
                return name;
        }

        public Product setName(String name) {
                this.name = name;
                return this;
        }

        public String description() {
                return description;
        }

        public Product setDescription(String description) {
                this.description = description;
                return this;
        }

        public String image() {
                return image;
        }

        public Product setImage(String image) {
                this.image = image;
                return this;
        }

        public String category() {
                return category;
        }

        public Product setCategory(String category) {
                this.category = category;
                return this;
        }

        public float price() {
                return price;
        }

        public Product setPrice(float price) {
                this.price = price;
                return this;
        }

        public int quantity() {
                return quantity;
        }

        public Product setQuantity(int quantity) {
                this.quantity = quantity;
                return this;
        }

        public String internalReference() {
                return internalReference;
        }

        public Product setInternalReference(String internalReference) {
                this.internalReference = internalReference;
                return this;
        }

        public int shellId() {
                return shellId;
        }

        public Product setShellId(int shellId) {
                this.shellId = shellId;
                return this;
        }

        public InventoryStatus inventoryStatus() {
                return inventoryStatus;
        }

        public Product setInventoryStatus(InventoryStatus inventoryStatus) {
                this.inventoryStatus = inventoryStatus;
                return this;
        }

        public short rating() {
                return rating;
        }

        public Product setRating(short rating) {
                this.rating = rating;
                return this;
        }

        public Long createdAt() {
                return createdAt;
        }

        public Product setCreatedAt(Long createdAt) {
                this.createdAt = createdAt;
                return this;
        }

        public Long updatedAt() {
                return updatedAt;
        }

        public Product setUpdatedAt(Long updatedAt) {
                this.updatedAt = updatedAt;
                return this;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Product product = (Product) o;
                return Float.compare(product.price, price) == 0 && quantity == product.quantity && shellId == product.shellId && rating == product.rating && Objects.equals(id, product.id) && Objects.equals(code, product.code) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(image, product.image) && Objects.equals(category, product.category) && Objects.equals(internalReference, product.internalReference) && inventoryStatus == product.inventoryStatus && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, code, name, description, image, category, price, quantity, internalReference, shellId, inventoryStatus, rating, createdAt, updatedAt);
        }
}

