package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.payloads.AddUserRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.UpdateUserRequest;

public interface UserService {
    ApiResponse addUser(AddUserRequest addUserRequest) throws Exception;
    ApiResponse updateUser(UpdateUserRequest updateUserRequest, Integer userId) throws Exception;
}
