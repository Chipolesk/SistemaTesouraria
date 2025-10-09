package org.exemploTesouraria.Controller;

import jakarta.validation.Valid;
import org.exemploTesouraria.DTO.UserDTO;
import org.exemploTesouraria.DTO.UserRequestDTO;
import org.exemploTesouraria.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserRequestDTO requestDTO){
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDTO.getName()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
      List<UserDTO> users = userService.showUsers();
      return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name){
        userService.deleteUser(name);
        return ResponseEntity.noContent().build(); //retorna um 204 No Content, "excluido com sucesso, sem corpo na resposta"
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody @Valid UserRequestDTO requestDTO){
        UserDTO updateUser = userService.updateUser(id, requestDTO.getName());
        return ResponseEntity.ok(updateUser);
    }
}
