package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.Users;

public record UserDTO(Integer id, String name) {


    public static UserDTO fromEntity(Users user){
      return new UserDTO(user.getId() ,user.getName());
    }

}
