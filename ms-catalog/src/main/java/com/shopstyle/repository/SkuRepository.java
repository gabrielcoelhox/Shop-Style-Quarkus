package com.shopstyle.repository;

import com.shopstyle.domain.model.Product;
import com.shopstyle.domain.model.Sku;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SkuRepository implements PanacheRepository<Sku> {
}