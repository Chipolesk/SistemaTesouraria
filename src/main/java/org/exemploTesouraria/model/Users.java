package org.exemploTesouraria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_UPA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Column(name = "id_user", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "name_user", unique = true, length = 50)
    private String name;
}
