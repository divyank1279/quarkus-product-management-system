package com.api;

import com.api.banking.entity.ProductEntity;
import com.api.banking.repository.ProductRepository;
import com.api.banking.service.ProductServiceImpl;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private static final Long PRODUCT_ID = 1L;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<ProductEntity> mockList = Arrays.asList(new ProductEntity(), new ProductEntity());
        when(productRepository.listAll()).thenReturn(mockList);

        List<ProductEntity> result = productService.getAll();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).listAll();
    }

    @Test
    void testGetByIdFound() {
        ProductEntity product = new ProductEntity();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(product);

        ProductEntity result = productService.getById(PRODUCT_ID);
        assertNotNull(result);
    }

    @Test
    void testGetByIdNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> productService.getById(PRODUCT_ID));
    }

    @Test
    void testCreate() {
        ProductEntity product = new ProductEntity();
        ProductEntity result = productService.create(product);

        assertEquals(product, result);
        verify(productRepository).persist(product);
    }

    @Test
    void testUpdateSuccess() {
        ProductEntity existing = new ProductEntity();
        existing.setQuantity(5);

        ProductEntity update = new ProductEntity();
        update.setName("New Name");
        update.setDescription("Desc");
        update.setPrice(100.0);
        update.setQuantity(10);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(existing);

        ProductEntity result = productService.update(PRODUCT_ID, update);

        assertEquals("New Name", result.getName());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void testUpdateNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(null);

        ProductEntity update = new ProductEntity();
        assertThrows(NotFoundException.class, () -> productService.update(PRODUCT_ID, update));
    }

    @Test
    void testDeleteSuccess() {
        when(productRepository.deleteById(PRODUCT_ID)).thenReturn(true);

        assertDoesNotThrow(() -> productService.delete(PRODUCT_ID));
    }

    @Test
    void testDeleteNotFound() {
        when(productRepository.deleteById(PRODUCT_ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> productService.delete(PRODUCT_ID));
    }

    @Test
    void testStockAvailableTrue() {
        ProductEntity product = new ProductEntity();
        product.setQuantity(10);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(product);

        assertTrue(productService.isStockAvailable(PRODUCT_ID, 5));
    }

    @Test
    void testStockAvailableFalse() {
        ProductEntity product = new ProductEntity();
        product.setQuantity(3);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(product);

        assertFalse(productService.isStockAvailable(PRODUCT_ID, 5));
    }

    @Test
    void testStockAvailable_ProductNotFound() {
        when(productRepository.findById(2L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            productService.isStockAvailable(2L, 5);
        });
    }

    @Test
    void testSortedByPriceAsc() {
        List<ProductEntity> list = Arrays.asList(new ProductEntity(), new ProductEntity());

        PanacheQuery<ProductEntity> query = mockQuery(list);
        when(productRepository.find("ORDER BY price ASC")).thenReturn(query);

        List<ProductEntity> result = productService.getAllSortedByPrice("asc");
        assertEquals(2, result.size());
    }

    @Test
    void testSortedByPriceDesc() {
        List<ProductEntity> list = Arrays.asList(new ProductEntity(), new ProductEntity());

        PanacheQuery<ProductEntity> query = mockQuery(list);
        when(productRepository.find("ORDER BY price DESC")).thenReturn(query);

        List<ProductEntity> result = productService.getAllSortedByPrice("desc");

        assertEquals(2, result.size());
    }

    @SuppressWarnings("unchecked")
    private io.quarkus.hibernate.orm.panache.PanacheQuery<ProductEntity> mockQuery(List<ProductEntity> resultList) {
        io.quarkus.hibernate.orm.panache.PanacheQuery<ProductEntity> query = (io.quarkus.hibernate.orm.panache.PanacheQuery<ProductEntity>) mock(
                io.quarkus.hibernate.orm.panache.PanacheQuery.class);

        doReturn(resultList).when(query).list();
        return query;
    }

}
