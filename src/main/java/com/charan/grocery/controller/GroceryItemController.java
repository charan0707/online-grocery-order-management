
package com.charan.grocery.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.charan.grocery.dto.*;
import com.charan.grocery.service.GroceryItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grocery-items")
public class GroceryItemController {

    private final GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroceryItemResponse createGroceryItem(
            @Valid @RequestBody GroceryItemRequest request) {
        return groceryItemService.createGroceryItem(request);
    }

    @GetMapping("/{id}")
    public GroceryItemResponse getGroceryItemById(@PathVariable Long id) {
        return groceryItemService.getGroceryItemById(id);
    }

    @GetMapping
    public List<GroceryItemResponse> getAllGroceryItems() {
        return groceryItemService.getAllGroceryItems();
    }

    @PutMapping("/{id}")
    public GroceryItemResponse updateGroceryItem(
            @PathVariable Long id,
            @Valid @RequestBody GroceryItemRequest request) {
        return groceryItemService.updateGroceryItem(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroceryItem(@PathVariable Long id) {
        groceryItemService.deleteGroceryItem(id);
    }
}
