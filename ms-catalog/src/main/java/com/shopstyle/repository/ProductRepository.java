package com.shopstyle.repository;

import com.shopstyle.domain.model.Media;
import com.shopstyle.domain.model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
}