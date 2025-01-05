package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class WishList{
        @Id @GeneratedValue
        Long id;
        @OneToOne
        UserAccount user;
        @OneToMany
        List<Product> productList;

        public Long id() {
                return id;
        }

        public WishList setId(Long id) {
                this.id = id;
                return this;
        }

        public UserAccount user() {
                return user;
        }

        public WishList setUser(UserAccount user) {
                this.user = user;
                return this;
        }

        public List<Product> productList() {
                return productList;
        }

        public WishList setProductList(List<Product> productList) {
                this.productList = productList;
                return this;
        }
}

