package org.exemploTesouraria.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome pode ter no máximo 50 caracteres")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}