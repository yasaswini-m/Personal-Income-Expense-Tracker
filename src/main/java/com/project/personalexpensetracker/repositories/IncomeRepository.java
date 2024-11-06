package com.project.personalexpensetracker.repositories;

import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income,Long> {
    List<Income> findByDateBetween(LocalDate startdate,LocalDate enddate);

    @Query("SELECT SUM(i.amount) FROM Income i")
    Double sumAllAmount();

    Optional<Income> findFirstByOrderByDateDesc();
}
