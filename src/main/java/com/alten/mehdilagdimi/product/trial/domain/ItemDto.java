package com.alten.mehdilagdimi.product.trial.domain;

public class ItemDto {
        Long id;
        ProductDtoResp product;
        int quantity;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public ProductDtoResp getProduct() {
                return product;
        }

        public void setProduct(ProductDtoResp product) {
                this.product = product;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }
}

