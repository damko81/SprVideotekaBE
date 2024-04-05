package tech.damko.videoteka.service;

import tech.damko.videoteka.dto.LoginDTO;
import tech.damko.videoteka.dto.UsersDTO;
import tech.damko.videoteka.response.LoginResponse;

public interface UsersService {

    String addUsers(UsersDTO employeeDTO);
    LoginResponse loginUsers(LoginDTO loginDTO);
}
