package com.api.watchshop.controller;

import com.api.watchshop.dto.ProductRequest;
import com.api.watchshop.dto.ProductResponse;
import com.api.watchshop.entity.Category;
import com.api.watchshop.service.CategoryService;
import com.api.watchshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createNewProduct(@RequestBody ProductRequest productRequest){
        ProductResponse response = productService.create(productRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getPageProducts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "title") String sortBy,
                                                                 @RequestParam(defaultValue = "desc") String sortOrder){
        return ResponseEntity.ok(productService.getPageProducts(page, size, sortBy, sortOrder));
    }

    @GetMapping("/category/{url}")
    public ResponseEntity<List<ProductResponse>> getPageProductsByCategory(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "title") String sortBy,
                                                                           @RequestParam(defaultValue = "desc") String sortOrder,
                                                                           @PathVariable String url
                                                                           ){
        Category category = categoryService.findByUrl(url);
        return  ResponseEntity.ok(productService.getPageProductsByCategory(page,size,sortBy, sortOrder, category));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable long id, @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.update(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.delete(id));
    }
}
