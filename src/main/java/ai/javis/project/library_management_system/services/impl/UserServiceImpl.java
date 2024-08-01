package ai.javis.project.library_management_system.services.impl;

import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.User;
import ai.javis.project.library_management_system.payloads.AddUserRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.UpdateUserRequest;
import ai.javis.project.library_management_system.repositories.UserRepository;
import ai.javis.project.library_management_system.services.UserService;
import ai.javis.project.library_management_system.utils.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public ApiResponse addUser(AddUserRequest addUserRequest) throws Exception{
        User user = userRepository.findByEmail(addUserRequest.getEmail());
        if(user != null){
            return new ApiResponse("", "User already exists", false);
        }
        User newUser = CustomMapper.addUserRequestToUserMapper(addUserRequest);
        newUser = userRepository.save(newUser);
        return new ApiResponse("User created successfully", "",true);
    }

    @Override
    @Transactional
    public ApiResponse updateUser(UpdateUserRequest updateUserRequest, Integer userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "userId", userId));
        if(updateUserRequest.getName() != null){
            user.setName(updateUserRequest.getName());
        }
        if(updateUserRequest.getEmail() != null){
            user.setEmail(updateUserRequest.getEmail());
        }
        user = userRepository.save(user);
        return new ApiResponse("User updated successfully", "", true);
    }
}
