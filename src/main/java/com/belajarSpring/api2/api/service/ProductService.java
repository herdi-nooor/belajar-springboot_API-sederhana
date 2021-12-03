package com.belajarSpring.api2.api.service;

import com.belajarSpring.api2.api.dto.product.ProductDTO;
import com.belajarSpring.api2.api.entity.Category;
import com.belajarSpring.api2.api.entity.Product;
import com.belajarSpring.api2.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(ProductDTO productDTO, Category category){
        Product product = getProductFromDto( productDTO, category);
        productRepository.save(product);
    }

    private static Product getProductFromDto(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setImageURL(productDTO.getImageURL());
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return product;
    }
    // list of all products
    public List<ProductDTO> listProduct() {
        // first fetch all products
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products){
            // for esch product change it to DTO
            productDTOS.add(new ProductDTO(product));
        }
        return productDTOS;
    }

    // update a product
    public void updateProduct(Integer productId, ProductDTO productDTO, Category category){
        Product product = getProductFromDto(productDTO, category);
        // set the id for updating
        product.setId(productId);
        // update
        productRepository.save(product);
    }
}
