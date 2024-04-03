package tech.damko.videoteka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.damko.videoteka.service.UsersService;
import tech.damko.videoteka.model.Users;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersResource {

    private final UsersService usersService;

    public UsersResource(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/All")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id){
        Users user = usersService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user){
        Users newUser = usersService.createUser(user);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Users> updateMovie(@RequestBody Users user){
        Users updateMovie = usersService.updateUser(user);
        return new ResponseEntity<>(updateMovie,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        usersService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
