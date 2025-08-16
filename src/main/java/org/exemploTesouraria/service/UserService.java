package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public UserDTO createUser(String name){
        Optional<Users> userExists = userRepository.findByName(name);

        if (userExists.isPresent()) {
            throw new RuntimeException("Este membro já existe");
        }

        Users newUser = new Users();
        newUser.setName(name);
        try {
            Users saved = userRepository.save(newUser);
            return new UserDTO(saved);
        }catch (DataIntegrityViolationException exception){
            throw new RuntimeException("Já existe um membro com este nome");
        }

    }

    public List<Users> showUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(String name){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Este Membro não existe"));

        userRepository.delete(users);

    }
}
