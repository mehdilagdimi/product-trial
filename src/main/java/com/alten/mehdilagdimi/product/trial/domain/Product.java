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

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public float getPrice() {
                return price;
        }

        public void setPrice(float price) {
                this.price = price;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public String getInternalReference() {
                return internalReference;
        }

        public void setInternalReference(String internalReference) {
                this.internalReference = internalReference;
        }

        public int getShellId() {
                return shellId;
        }

        public void setShellId(int shellId) {
                this.shellId = shellId;
        }

        public InventoryStatus getInventoryStatus() {
                return inventoryStatus;
        }

        public void setInventoryStatus(InventoryStatus inventoryStatus) {
                this.inventoryStatus = inventoryStatus;
        }

        public short getRating() {
                return rating;
        }

        public void setRating(short rating) {
                this.rating = rating;
        }

        public Long getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Long createdAt) {
                this.createdAt = createdAt;
        }

        public Long getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(Long updatedAt) {
                this.updatedAt = updatedAt;
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

