package com.api;

import com.api.banking.entity.ProductEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductResourceTest {

    private static Long testProductId;

    @Test
    @Order(1)
    public void testCreateProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.00);
        product.setQuantity(10);

        testProductId = given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Product"))
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    @Order(2)
    public void testGetProductById() {
        given()
                .when()
                .get("/products/" + testProductId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Product"))
                .body("quantity", equalTo(10));
    }

    @Test
    @Order(3)
    public void testUpdateProduct() {
        ProductEntity updated = new ProductEntity();
        updated.setName("Updated Product");
        updated.setDescription("Test Description");
        updated.setPrice(199.99);
        updated.setQuantity(10);

        given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put("/products/" + testProductId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Product"))
                .body("price", equalTo(199.99F));
    }

    @Test
    @Order(4)
    public void testGetAllProducts() {
        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(5)
    public void testCheckStockAvailableTrue() {
        given()
                .queryParam("count", 3)
                .when()
                .get("/products/" + testProductId + "/check-stock")
                .then()
                .statusCode(200)
                .body("available", is(true));
    }

    @Test
    @Order(6)
    public void testCheckStockAvailableFalse() {
        given()
                .queryParam("count", 12)
                .when()
                .get("/products/" + testProductId + "/check-stock")
                .then()
                .statusCode(200)
                .body("available", is(false));
    }

    @Test
    @Order(7)
    public void testDeleteProduct() {
        given()
                .when()
                .delete("/products/" + testProductId)
                .then()
                .statusCode(204); // No Content
    }

    @Test
    @Order(8)
    public void testGetDeletedProductShouldReturn404() {
        given()
                .when()
                .get("/products/" + testProductId)
                .then()
                .statusCode(404);
    }
}
