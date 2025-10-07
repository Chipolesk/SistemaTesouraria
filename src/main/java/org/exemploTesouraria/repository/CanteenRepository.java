package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CanteenRepository extends JpaRepository<Canteen, Integer> {
    @Override
    List<Canteen> findAll();
    List<MonthEnum> findByMonth(MonthEnum month);
    List<Users> findByUsers(Users users);
    List<Canteen> findByNameDebtors(String nameDebtors);
    Optional<Canteen> findById(Integer id);
    Optional<Canteen> findByDateCant(Date dateCant);
}
