package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Cart {
        @Id
        @GeneratedValue
        Long id;
        @ManyToOne
        UserAccount user;

        @OneToMany(cascade = CascadeType.ALL)
        Set<Item> items;

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

        public Set<Item> getItems() {
                return items;
        }

        public void setItems(Set<Item> items) {
                this.items = items;
        }

        //        public Long id() {
//                return id;
//        }
//
//        public Cart setId(Long id) {
//                this.id = id;
//                return this;
//        }
//
//        public UserAccount userAccount() {
//                return userAccount;
//        }
//
//        public Cart setUser(UserAccount userAccount) {
//                this.userAccount = userAccount;
//                return this;
//        }
//
//        public Set<Item> items() {
//                return items;
//        }
//
//        public Cart setItems(Set<Item> items) {
//                this.items = items;
//                return this;
//        }
}

