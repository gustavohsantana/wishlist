package com.project.wishlist.steps;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;
import com.project.wishlist.repository.WishListRepository;
import com.project.wishlist.service.WishListService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WishListSteps {

    @Autowired
    private WishListService wishListService;

    private String clientId;
    private ProductDTO productDTO;
    private WishListDTO wishListDTO;
    private Exception exception;

    @Autowired
    private WishListRepository wishListRepository;
    boolean isProductInWishlist = false;

    @Given("the client with ID {string}")
    public void theClientWithID(String clientId) {
        wishListRepository.deleteAll();
        this.clientId = clientId;
    }

    @When("the client adds a product with ID {string} and name {string}")
    public void theClientAddsAProductWithIDAndName(String productId, String productName) {
        productDTO = ProductDTO.builder().id(productId).name(productName).build();
        try {
            wishListDTO = wishListService.addProductToWishList(clientId, productDTO);
        } catch (RuntimeException e) {
            exception = e;
        }
    }

    @Then("the product should be added to the wishlist")
    public void theProductShouldBeAddedToTheWishlist() {
        assertEquals(1, wishListDTO.getProducts().size());
        assertEquals(productDTO.getId(), wishListDTO.getProducts().get(0).getId());
        assertEquals(productDTO.getName(), wishListDTO.getProducts().get(0).getName());
    }

    @Given("the client has a product with ID {string} and name {string} in the wishlist")
    public void theClientHasAProductWithIDAndNameInTheWishlist(String productId, String productName) {
        productDTO = ProductDTO.builder().id(productId).name(productName).build();
        wishListService.addProductToWishList(clientId, productDTO);
    }

    @When("the client removes the product with ID {string}")
    public void theClientRemovesTheProductWithID(String productId) {
        wishListDTO = wishListService.removeProductFromWishList(clientId, productId);
    }

    @Then("the product should not be in the wishlist")
    public void theProductShouldNotBeInTheWishlist() {
        assertEquals(0, wishListDTO.getProducts().size());
    }

    @Given("the wishlist contains products")
    public void theWishlistContainsProducts() {
        for (int i = 1; i <= 3; i++) {
            ProductDTO product = ProductDTO.builder()
                    .id(String.valueOf(i))
                    .name("Produto " + i)
                    .build();
            wishListService.addProductToWishList(clientId, product);
        }
    }

    @When("the client retrieves their wishlist")
    public void theClientRetrievesTheirWishlist() {
        try {
            wishListDTO = WishListDTO.builder().products(wishListService.getAllProductsFromWishList(clientId)).build();
        } catch (RuntimeException e) {
            exception = e;
        }
    }

    @Then("the system should return all products in the wishlist")
    public void theSystemShouldReturnAllProductsInTheWishlist() {
        assertEquals(3, wishListDTO.getProducts().size());
    }

    @Given("the wishlist already contains 20 products")
    public void theWishlistAlreadyContains20Products() {
        for (int i = 1; i <= 20; i++) {
            ProductDTO product = ProductDTO.builder()
                    .id(String.valueOf(i))
                    .name("Produto " + i)
                    .build();
            wishListService.addProductToWishList(clientId, product);
        }
    }

    @Then("the system should not allow the product to be added")
    public void theSystemShouldNotAllowTheProductToBeAdded() {
        exception = assertThrows(RuntimeException.class, () -> {
            wishListService.addProductToWishList(clientId, productDTO);
        });
    }

    @Then("the system should return a message saying {string}")
    public void theSystemShouldReturnAMessageSaying(String expectedMessage) {
        assertEquals(expectedMessage, exception.getMessage());
    }

    @When("the client checks if the product with ID {string} is in the wishlist")
    public void theClientChecksIfTheProductWithIDIsInTheWishlist(String productId) {
        isProductInWishlist = wishListService.isProductInWishList(clientId, productId);
    }

    @Then("the system should confirm that the product is in the wishlist")
    public void theSystemShouldConfirmThatTheProductIsInTheWishlist() {
        assertTrue(isProductInWishlist, "The product is not in the wishlist");
    }

    @When("the client adds a product with ID {string} and name {string} again")
    public void theClientAddsAProductWithIDAndNameAgain(String productId, String productName) {
        productDTO = ProductDTO.builder().id(productId).name(productName).build();
        try {
            wishListDTO = wishListService.addProductToWishList(clientId, productDTO);
        } catch (RuntimeException e) {
            exception = e;
        }
    }

    @Then("the system should not allow the duplicate product to be added")
    public void theSystemShouldNotAllowTheDuplicateProductToBeAdded() {
        exception = assertThrows(RuntimeException.class, () -> {
            wishListService.addProductToWishList(clientId, productDTO);
        });
    }

    @Given("the wishlist is empty")
    public void theWishlistIsEmpty() {
        wishListRepository.deleteAll();
    }

    @Then("the system should return an empty list")
    public void theSystemShouldReturnAnEmptyList() {
        assertNotNull(exception);
        assertEquals("Wishlist não encontrada para o clienteId: " + clientId, exception.getMessage());
    }

    @Then("the system should remove all products from the wishlist")
    public void theSystemShouldRemoveAllProductsFromTheWishlist() {
        wishListDTO = WishListDTO.builder().products(wishListService.getAllProductsFromWishList(clientId)).build();
        assertTrue(wishListDTO.getProducts().isEmpty(), "The wishlist is not empty");
    }
}