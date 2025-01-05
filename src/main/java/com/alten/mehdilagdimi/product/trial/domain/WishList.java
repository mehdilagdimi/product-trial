package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class WishList{
        @Id @GeneratedValue
        Long id;
        @OneToOne
        UserAccount user;
        @OneToMany
        List<Product> productList;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public UserAccount getUser() {
                return user;
        }

        public void setUser(UserAccount user) {
                this.user = user;
        }

        public List<Product> getProductList() {
                return productList;
        }

        public void setProductList(List<Product> productList) {
                this.productList = productList;
        }

}

