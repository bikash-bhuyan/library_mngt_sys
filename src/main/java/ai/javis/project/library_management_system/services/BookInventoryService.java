package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.payloads.AddBookToInventoryRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;

public interface BookInventoryService {
    ApiResponse addBookToInventory(AddBookToInventoryRequest addBookToInventoryRequest) throws Exception;
    ApiResponse deleteBookFromInventory(Integer bookInventoryId) throws Exception;
}
