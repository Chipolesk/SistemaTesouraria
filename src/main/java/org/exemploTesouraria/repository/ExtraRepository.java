package org.exemploTesouraria.repository;

import org.exemploTesouraria.model.Extras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraRepository extends JpaRepository<Extras, Integer> {

    Optional<Extras> findByDateExtra(LocalDate dateExtra);

    List<Extras> findByDateExtraBetween(LocalDate startDate, LocalDate endDate);
}
