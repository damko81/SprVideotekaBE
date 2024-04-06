package tech.damko.videoteka.service;

import tech.damko.videoteka.dto.LoginDTO;
import tech.damko.videoteka.dto.UsersDTO;
import tech.damko.videoteka.model.Users;
import tech.damko.videoteka.response.LoginResponse;

import java.util.List;

public interface UsersService {

    List<Users> findAllUsers();
    String addUsers(UsersDTO employeeDTO);
    LoginResponse loginUsers(LoginDTO loginDTO);
}
