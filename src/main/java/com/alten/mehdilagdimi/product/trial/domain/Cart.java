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

        public Long id() {
                return id;
        }

        public Cart setId(Long id) {
                this.id = id;
                return this;
        }

        public UserAccount user() {
                return user;
        }

        public Cart setUser(UserAccount user) {
                this.user = user;
                return this;
        }

        public Set<Item> items() {
                return items;
        }

        public Cart setItems(Set<Item> items) {
                this.items = items;
                return this;
        }
}

