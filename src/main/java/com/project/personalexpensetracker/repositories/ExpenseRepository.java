package com.project.personalexpensetracker.repositories;

import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByDateBetween(LocalDate startdate, LocalDate enddate);

    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double sumAllAmount();

    Optional<Expense> findFirstByOrderByDateDesc();
}
