package com.api.banking.service;

import com.api.banking.entity.ProductEntity;
import com.api.banking.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.listAll();
    }

    @Override
    public ProductEntity getById(Long id) {
        ProductEntity product = productRepository.findById(id);
        if (product == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
        return product;
    }

    @Override
    @Transactional
    public ProductEntity create(ProductEntity productEntity) {
        productRepository.persist(productEntity);
        return productEntity;
    }

    @Override
    @Transactional
    public ProductEntity update(Long id, ProductEntity updatedProductEntity) {
        ProductEntity existing = productRepository.findById(id);
        if (existing == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }

        existing.setName(updatedProductEntity.getName());
        existing.setDescription(updatedProductEntity.getDescription());
        existing.setPrice(updatedProductEntity.getPrice());
        existing.setQuantity(updatedProductEntity.getQuantity());

        return existing;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        boolean deleted = productRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
    }

    @Override
    public boolean isStockAvailable(Long id, int count) {
        ProductEntity product = productRepository.findById(id);
        if (product == null) {
            throw new NotFoundException("Product not found with ID " + id);
        }
        return product.getQuantity() >= count;
    }

    @Override
    public List<ProductEntity> getAllSortedByPrice(String sortOrder) {
        String orderClause = "asc".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";
        return productRepository.find("ORDER BY price " + orderClause).list();
    }

}
