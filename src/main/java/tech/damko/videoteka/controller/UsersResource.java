package tech.damko.videoteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.damko.videoteka.dto.LoginDTO;
import tech.damko.videoteka.dto.UsersDTO;
import tech.damko.videoteka.response.LoginResponse;
import tech.damko.videoteka.service.UsersService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersResource {

    @Autowired
    private final UsersService usersService;

    public UsersResource(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/save")
    public String saveUsers(@RequestBody UsersDTO usersDTO)
    {
        String id = usersService.addUsers(usersDTO);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = usersService.loginUsers(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}
