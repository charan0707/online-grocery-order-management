
package com.charan.grocery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.charan.grocery.dto.GroceryItemRequest;
import com.charan.grocery.dto.GroceryItemResponse;
import com.charan.grocery.entity.GroceryItem;
import com.charan.grocery.exception.ResourceNotFoundException;
import com.charan.grocery.repository.GroceryItemRepository;

@Service
public class GroceryItemService {

    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    private GroceryItemResponse convertToResponse(GroceryItem item) {
        GroceryItemResponse response = new GroceryItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setCategory(item.getCategory());
        response.setPrice(item.getPrice());
        response.setQuantity(item.getQuantity());
        return response;
    }

    public GroceryItemResponse createGroceryItem(GroceryItemRequest request) {
        GroceryItem item = new GroceryItem();
        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());

        return convertToResponse(groceryItemRepository.save(item));
    }

    public GroceryItemResponse getGroceryItemById(Long id) {
        GroceryItem item = groceryItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Grocery item not found with id: " + id));
        return convertToResponse(item);
    }

    public List<GroceryItemResponse> getAllGroceryItems() {
        return groceryItemRepository.findAll()
            .stream().map(this::convertToResponse).toList();
    }

    public GroceryItemResponse updateGroceryItem(Long id, GroceryItemRequest request) {
        GroceryItem item = groceryItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Grocery item not found with id: " + id));

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());

        return convertToResponse(groceryItemRepository.save(item));
    }

    public void deleteGroceryItem(Long id) {
        GroceryItem item = groceryItemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Grocery item not found with id: " + id));
        groceryItemRepository.delete(item);
    }
}
