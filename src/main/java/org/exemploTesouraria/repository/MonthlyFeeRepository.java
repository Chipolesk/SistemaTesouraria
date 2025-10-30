package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.MonthlyFee;
import org.exemploTesouraria.model.Users;
import org.exemploTesouraria.model.enums.MonthEnum;
import org.exemploTesouraria.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Integer> {

    List<MonthlyFee> findByMonth(MonthEnum month);
    List<MonthlyFee> findByUsers(Users users);
    Optional<MonthlyFee> findByUsersAndMonth(Users users, MonthEnum month);



}
