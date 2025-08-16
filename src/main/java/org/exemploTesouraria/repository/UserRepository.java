package org.exemploTesouraria.repository;


import org.exemploTesouraria.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByName(String nameUser);

    void delete(Users users);
}