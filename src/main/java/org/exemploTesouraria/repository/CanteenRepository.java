package org.exemploTesouraria.repository;

import org.exemploTesouraria.DTO.CanteenDTO;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CanteenRepository extends JpaRepository<Canteen, Integer> {

    Optional<Canteen> findById(Integer id);
    Optional<Canteen> findByDateCant(LocalDate dateCant);
    List<Canteen> findByDateCantBetween(LocalDate startDate, LocalDate endDate);


}
