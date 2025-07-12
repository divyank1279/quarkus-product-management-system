package com.api.banking.resource;

import com.api.banking.entity.ProductEntity;
import com.api.banking.service.ProductService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST resource class to handle HTTP requests for Product operations.
 * Acts as the Controller layer in the application architecture.
 */
@Path("/products") // Base path for all endpoints in this resource
@Produces(MediaType.APPLICATION_JSON) // Response content type
@Consumes(MediaType.APPLICATION_JSON) // Request content type
public class ProductResource {

    // Injecting the service layer that contains business logic
    @Inject
    ProductService productService;

    /**
     * GET /products
     * Retrieves all product records from the system.
     * 
     * @return List of all ProductEntity objects
     */
    @GET
    public List<ProductEntity> getAll() {
        return productService.getAll();
    }

    /**
     * GET /products/{id}
     * Retrieves a specific product by its ID.
     *
     * @param id The unique identifier of the product
     * @return ProductEntity if found, otherwise throws NotFoundException
     */
    @GET
    @Path("/{id}")
    public ProductEntity getById(@PathParam("id") Long id) {
        return productService.getById(id);
    }

    /**
     * POST /products
     * Creates a new product entry in the system.
     *
     * @param productEntity The product data to be persisted
     * @return The created ProductEntity
     */
    @POST
    public ProductEntity create(ProductEntity productEntity) {
        return productService.create(productEntity);
    }

    /**
     * PUT /products/{id}
     * Updates an existing product by its ID.
     *
     * @param id                   The ID of the product to update
     * @param updatedProductEntity The new data for the product
     * @return The updated ProductEntity
     */
    @PUT
    @Path("/{id}")
    public ProductEntity update(@PathParam("id") Long id, ProductEntity updatedProductEntity) {
        return productService.update(id, updatedProductEntity);
    }

    /**
     * DELETE /products/{id}
     * Deletes a product from the system by its ID.
     *
     * @param id The ID of the product to delete
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        productService.delete(id);
    }

    /**
     * Endpoint to check if the specified quantity of a product is available in
     * stock.
     *
     * @param id    The ID of the product to check.
     * @param count The requested quantity to verify availability against.
     * @return A JSON map indicating whether the requested quantity is available.
     *         Example response: { "available": true }
     */
    @GET
    @Path("/{id}/check-stock")
    public Map<String, Boolean> checkStock(@PathParam("id") Long id, @QueryParam("count") int count) {
        boolean available = productService.isStockAvailable(id, count);
        return Collections.singletonMap("available", available);
    }

    /**
     * Endpoint to retrieve all products sorted in ascending order by price.
     *
     * @return A list of ProductEntity objects sorted by price from lowest to
     *         highest.
     */
    @GET
    @Path("/sorted-by-price")
    public List<ProductEntity> getAllSortedByPrice(@QueryParam("sortOrder") String sortOrder) {
        return productService.getAllSortedByPrice(sortOrder);
    }

}
