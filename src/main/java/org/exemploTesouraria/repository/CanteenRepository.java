package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CanteenRepository extends JpaRepository<Canteen, Integer> {
    @Override
    List<Canteen> findAll();
    List<Canteen> findByMonth(MonthEnum month);
    List<Canteen> findByUsers(Users users);
    List<Canteen> findByNameDebtors(String nameDebtors);
    Optional<Canteen> findByDateCant(Date dateCant);
}
