package com.belajarSpring.api2.api.controler;

import com.belajarSpring.api2.api.dto.product.ProductDTO;
import com.belajarSpring.api2.api.entity.Category;
import com.belajarSpring.api2.api.entity.Product;
import com.belajarSpring.api2.api.helper.ApiResponse;
import com.belajarSpring.api2.api.service.CategoryService;
import com.belajarSpring.api2.api.service.ProductService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody @NotNull ProductDTO productDTO){
        Optional<Category> optionalCategory = categoryService.readCategory(productDTO.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDTO, category);
        return new ResponseEntity<>(new ApiResponse(true, "product has been added"), HttpStatus.CREATED);
    }

    // list all the products
    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProduct() {
        List<ProductDTO> productDTOS = productService.listProduct();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    // update a product
    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid ProductDTO productDTO){
        Optional<Category> optionalCategory = categoryService.readCategory(productDTO.getCategoryId());
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productId, productDTO, category);
        return new ResponseEntity<>(new ApiResponse(true, "product has been update"), HttpStatus.OK);
    }

}
