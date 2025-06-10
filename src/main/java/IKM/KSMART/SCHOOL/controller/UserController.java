package IKM.KSMART.SCHOOL.controller;

import IKM.KSMART.SCHOOL.contract.LoginResponse;
import IKM.KSMART.SCHOOL.contract.RegisterRequest;
import IKM.KSMART.SCHOOL.contract.UserRequest;
import IKM.KSMART.SCHOOL.contract.RegisterResponse;
import IKM.KSMART.SCHOOL.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public LoginResponse createUser(@RequestBody UserRequest request){
        return userService.login(request);

    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest request){
        return userService.register(request);
    }
}
