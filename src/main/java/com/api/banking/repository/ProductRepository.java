package com.api.banking.repository;

import com.api.banking.entity.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {
    // You can add custom DB queries here if needed in the future
}
