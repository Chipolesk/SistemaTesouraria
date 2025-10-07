package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtorRepository extends JpaRepository<Debtors, Integer> {

    List<Debtors> findAll();
    List<Debtors> findByName(String name);
    List<Debtors> findByCanteen(Canteen canteen);

}
