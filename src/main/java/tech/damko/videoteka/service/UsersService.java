package tech.damko.videoteka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.damko.videoteka.exception.UsersNotFoundException;
import tech.damko.videoteka.model.Users;
import tech.damko.videoteka.repo.UsersRepo;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    @Autowired
    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    public Users getUserById(Long id){
        return usersRepo.getUserById(id).orElseThrow(() -> new UsersNotFoundException("User by id " + id + " was not found."));
    }

    public Users createUser(Users user){
        return usersRepo.save(user);
    }

    public Users updateUser(Users user){
        return usersRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        usersRepo.deleteUserById(id);
    }
}
