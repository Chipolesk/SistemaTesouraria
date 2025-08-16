package org.exemploTesouraria.DTO;

import org.exemploTesouraria.model.Users;

public class UserDTO {

    private Integer id;
    private String name;

    public UserDTO() {}

    public UserDTO(Users user){
        this.id = user.getId();
        this.name = user.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
