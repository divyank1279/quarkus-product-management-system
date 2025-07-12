package com.api.banking.service;

import com.api.banking.entity.ProductEntity;

import java.util.List;

/**
 * Contract for managing product-related operations.
 * 
 * This interface defines the business operations that can be performed on
 * ProductEntity,
 * including CRUD operations. Implementations of this interface should provide
 * the actual
 * business logic.
 */
public interface ProductService {

    /**
     * Retrieves all products from the system.
     *
     * @return a list of all {@link ProductEntity} objects.
     */
    List<ProductEntity> getAll();

    /**
     * Retrieves a single product by its ID.
     *
     * @param id the unique identifier of the product to retrieve.
     * @return the {@link ProductEntity} with the given ID.
     * @throws jakarta.ws.rs.NotFoundException if no product is found with the given
     *                                         ID.
     */
    ProductEntity getById(Long id);

    /**
     * Creates and persists a new product in the system.
     *
     * @param productEntity the {@link ProductEntity} object to be created.
     * @return the newly created and persisted product.
     */
    ProductEntity create(ProductEntity productEntity);

    /**
     * Updates an existing product identified by the given ID.
     *
     * @param id                   the ID of the product to update.
     * @param updatedProductEntity the new product data to replace the existing one.
     * @return the updated {@link ProductEntity}.
     * @throws jakarta.ws.rs.NotFoundException if no product is found with the given
     *                                         ID.
     */
    ProductEntity update(Long id, ProductEntity updatedProductEntity);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     * @throws jakarta.ws.rs.NotFoundException if no product is found with the given
     *                                         ID.
     */
    void delete(Long id);

    /**
     * Checks if the specified quantity of a product is available in stock.
     *
     * @param id    the ID of the product.
     * @param count the quantity to check.
     * @return true if the quantity is available, false otherwise.
     */
    boolean isStockAvailable(Long id, int count);

    /**
     * Retrieves all products sorted in ascending order by their price.
     *
     * @return a list of ProductEntity objects sorted by price.
     */
    List<ProductEntity> getAllSortedByPrice(String sortOrder);

}
