package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtorRepository extends JpaRepository<Debtors, Integer> {

    List<Debtors> findByNameDebtors(String nameDebtors);
    List<Debtors> findByCanteen(Canteen canteen);
    Optional<Debtors> findByNameDebtorsAndCanteen(String nameDebtor, Canteen canteen);

}
