package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserAccount {

        @Id @GeneratedValue
        Long id;
        String username;
        String firstname;
        @Column(unique = true)
        String email;
        String password;
        @OneToMany(mappedBy = "user")
        List<Cart> cart;
        @OneToOne(mappedBy = "user")
        WishList wishList;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getFirstname() {
                return firstname;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public List<Cart> getCart() {
                return cart;
        }

        public void setCart(List<Cart> cart) {
                this.cart = cart;
        }

        public WishList getWishList() {
                return wishList;
        }

        public void setWishList(WishList wishList) {
                this.wishList = wishList;
        }

        //        public Long id() {
//                return id;
//        }
//
//        public UserAccount setId(Long id) {
//                this.id = id;
//                return this;
//        }
//
//        public String username() {
//                return username;
//        }
//
//        public UserAccount setUsername(String username) {
//                this.username = username;
//                return this;
//        }
//
//        public String firstname() {
//                return firstname;
//        }
//
//        public UserAccount setFirstname(String firstname) {
//                this.firstname = firstname;
//                return this;
//        }
//
//        public String email() {
//                return email;
//        }
//
//        public UserAccount setEmail(String email) {
//                this.email = email;
//                return this;
//        }
//
//        public String password() {
//                return password;
//        }
//
//        public UserAccount setPassword(String password) {
//                this.password = password;
//                return this;
//        }
//
//        public List<Cart> cart() {
//                return cart;
//        }
//
//        public UserAccount setCart(List<Cart> cart) {
//                this.cart = cart;
//                return this;
//        }
//
//        public WishList wishList() {
//                return wishList;
//        }
//
//        public UserAccount setWishList(WishList wishList) {
//                this.wishList = wishList;
//                return this;
//        }
//

}

