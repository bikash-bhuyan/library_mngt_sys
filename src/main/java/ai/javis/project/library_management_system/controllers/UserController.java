package ai.javis.project.library_management_system.controllers;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.payloads.AddUserRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.UpdateUserRequest;
import ai.javis.project.library_management_system.services.ReminderService;
import ai.javis.project.library_management_system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReminderService reminderService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody AddUserRequest addUserRequest){

        try{
            ApiResponse response = userService.addUser(addUserRequest);

            if(!response.getSuccess()){
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex){

            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(), false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable(name="userId") Integer userId){

        if(updateUserRequest.isEmpty()){
            ApiResponse apiResponse = new ApiResponse("", ResponseMessages.ATLEAST_PROVIDE_ONE_FIELD.getValue(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        if(updateUserRequest.getName() != null && updateUserRequest.getName().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("",ResponseMessages.USER_NAME_CANNOT_BE_BLANK.getValue(), false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }

        if(updateUserRequest.getEmail() != null && updateUserRequest.getEmail().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("",ResponseMessages.USER_EMAIL_CANNOT_BE_BLANK.getValue(), false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }

        try{
            ApiResponse response = userService.updateUser(updateUserRequest,userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping("/user/schedule")
//    public void userSchedule(){
//        reminderService.sendReminders();
//    }

}
