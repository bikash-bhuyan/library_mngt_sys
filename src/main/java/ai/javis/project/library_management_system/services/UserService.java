package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.User;
import ai.javis.project.library_management_system.payloads.AddUserRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.UpdateUserRequest;
import ai.javis.project.library_management_system.repositories.UserRepository;
import ai.javis.project.library_management_system.helpers.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserService {

    @Value("${user.prime_member.validity_in_days}")
    private Integer membershipDurationInDays;

    @Autowired
    private UserRepository userRepository;

    public ApiResponse addUser(AddUserRequest addUserRequest) throws Exception{
        User user = userRepository.findByEmail(addUserRequest.getEmail());

        if(user != null){
            return new ApiResponse("", ResponseMessages.USER_ALREADY_EXISTS.getValue(), false);
        }
        User newUser = CustomMapper.addUserRequestToUserMapper(addUserRequest);
        newUser = userRepository.save(newUser);
        return new ApiResponse(ResponseMessages.USER_CREATED_SUCCESSFULLY.getValue(), "",true);
    }


    public ApiResponse updateUser(UpdateUserRequest updateUserRequest, Integer userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "userId", userId));

        if(updateUserRequest.getName() != null){
            user.setName(updateUserRequest.getName());
        }

        if(updateUserRequest.getEmail() != null){
            user.setEmail(updateUserRequest.getEmail());
        }

        if(updateUserRequest.getPrimeMember() != null){
            if(updateUserRequest.getPrimeMember()){
                if(user.getPrimeMember()){
                    return new ApiResponse("", ResponseMessages.USER_ALREADY_REGISTERED_AS_PRIME_MEMBER.getValue() + " till " + user.getPrimeMemberValidity(), false);
                }
                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.add(Calendar.DAY_OF_YEAR,membershipDurationInDays);
                Date membershipExpiryDate = calendar.getTime();
                user.setPrimeMember(true);
                user.setPrimeMemberValidity(membershipExpiryDate);
            } else{
                user.setPrimeMember(false);
                user.setPrimeMemberValidity(null);
            }
        }
        user = userRepository.save(user);
        return new ApiResponse(ResponseMessages.USER_UPDATED_SUCCESSFULLY.getValue(), "", true);
    }
}
