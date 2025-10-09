package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public UserDTO createUser(String name){
        Optional<Users> userExists = userRepository.findByName(name);

        if (userExists.isPresent()) {
            throw DataConflictException.userAlreadyExist(name);
        }

        Users newUser = new Users();
        newUser.setName(name);

            Users saved = userRepository.save(newUser);
            return new UserDTO(saved);
    }

    public List<UserDTO> showUsers(){
        return userRepository.findAll()
                .stream() //stream serve para aplicar operações funcionais, como filter, map, collect
                .map(UserDTO::new) //mapeia toda a lista que veio do findAll e cria um novo UserDTO para cada.
                .collect(Collectors.toList()); //Converte em uma lista de UserDTO
    }

    public void deleteUser(String name){
        Users users = userRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.userNotFound(name));

        userRepository.delete(users);

    }
    public UserDTO updateUser(Integer id, String newName){
       Users users = userRepository.findById(id)
               .orElseThrow(() -> new  ResourceNotFoundException("Usuario não encontrado com o id: " + id));
       users.setName(newName);
       Users updated = userRepository.save(users);
        return new UserDTO(updated);
    }
}
