package tech.damko.videoteka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.damko.videoteka.dto.LoginDTO;
import tech.damko.videoteka.dto.UsersDTO;
import tech.damko.videoteka.model.Users;
import tech.damko.videoteka.repo.UsersRepo;
import tech.damko.videoteka.response.LoginResponse;

import java.util.Optional;

@Service
public class UsersIMPL implements UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUsers(UsersDTO usersDTO) {
        Users users = new Users(
                usersDTO.getId(),
                usersDTO.getName(),
                usersDTO.getUsername(),
                this.passwordEncoder.encode(usersDTO.getPassword())
        );
        usersRepo.save(users);
        return users.getName();
    }

    @Override
    public LoginResponse loginUsers(LoginDTO loginDTO) {
        String msg = "";
        Users users = usersRepo.findByUsername(loginDTO.getUsername());
        if (users != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = users.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Users> user = usersRepo.findOneByUsernameAndPassword(loginDTO.getUsername(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }
        }else {
            return new LoginResponse("Email not exits", false);
        }

    }
}
