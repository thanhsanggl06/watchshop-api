package com.api.watchshop.controller;

import com.api.watchshop.dto.CategoryRequest;
import com.api.watchshop.dto.CategoryResponse;
import com.api.watchshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }



    @PostMapping ("/create")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest){
        return new ResponseEntity(categoryService.create(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable long id, @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable long id){
        return  ResponseEntity.ok(categoryService.delete(id));
    }
}
