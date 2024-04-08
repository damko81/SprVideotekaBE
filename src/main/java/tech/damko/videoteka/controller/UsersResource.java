package tech.damko.videoteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.damko.videoteka.dto.LoginDTO;
import tech.damko.videoteka.dto.UsersDTO;
import tech.damko.videoteka.model.Users;
import tech.damko.videoteka.response.LoginResponse;
import tech.damko.videoteka.service.UsersService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UsersResource {

    @Autowired
    private final UsersService usersService;

    public UsersResource(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = usersService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/save")
    public String saveUsers(@RequestBody UsersDTO usersDTO)
    {
        String id = usersService.addUsers(usersDTO);
        return id;
    }

    @PutMapping("/update")
    public ResponseEntity<Users> updateUsers(@RequestBody UsersDTO usersDTO){
        Users updateUser = usersService.updateUsers(usersDTO);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
    //Obsolete: Vrača le txt in ne objekta Users
    @PostMapping(path = "/loginmsg")
    public ResponseEntity<?> loginUsersMsg(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = usersService.loginUsersMsg(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUsersRet(@RequestBody LoginDTO loginDTO)
    {
        Users user = usersService.loginUsers(loginDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable("id") Long id){
        usersService.deleteUsers(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
