package org.exemploTesouraria.Controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.DTO.UserRequestDTO;
import org.exemploTesouraria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserRequestDTO requestDTO){
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDTO.getName()));
    }
}
