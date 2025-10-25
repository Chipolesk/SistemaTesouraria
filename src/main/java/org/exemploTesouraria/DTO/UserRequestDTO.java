package org.exemploTesouraria.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO( @NotBlank(message = "O nome é obrigatório")
                             @Size(max = 50, message = "O nome pode ter no máximo 50 caracteres")
                             String name) { }