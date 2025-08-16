package org.exemploTesouraria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_UPA")
public class Users {

    @Column(name = "id_user", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "name_user", unique = true, length = 50)
    private String name;


    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
