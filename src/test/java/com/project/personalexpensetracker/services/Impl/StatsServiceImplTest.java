package com.project.personalexpensetracker.services.Impl;

import com.project.personalexpensetracker.dtos.GraphDTO;
import com.project.personalexpensetracker.dtos.StatsDTO;
import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import com.project.personalexpensetracker.entities.enums.ExpenseCategory;
import com.project.personalexpensetracker.entities.enums.IncomeCategory;
import com.project.personalexpensetracker.repositories.ExpenseRepository;
import com.project.personalexpensetracker.repositories.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    // Test for getChartData method
    @Test
    public void testGetChartData() {
        // Arrange
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(27);

        List<Expense> expenseList = List.of(
                new Expense(1L, "Groceries", "Weekly groceries", ExpenseCategory.GROCERIES, LocalDate.of(2024, 11, 1), 100),
                new Expense(2L, "Movies", "Movie night", ExpenseCategory.ENTERTAINMENT, LocalDate.of(2024, 11, 5), 50)
        );

        List<Income> incomeList = List.of(
                new Income(1L, "Salary", "Monthly salary", IncomeCategory.SALARY, LocalDate.of(2024, 11, 2), 1000),
                new Income(2L, "Freelance", "Freelance project", IncomeCategory.FREELANCE, LocalDate.of(2024, 11, 10), 300)
        );

        when(expenseRepository.findByDateBetween(startDate, endDate)).thenReturn(expenseList);
        when(incomeRepository.findByDateBetween(startDate, endDate)).thenReturn(incomeList);

        // Act
        GraphDTO result = statsService.getChartData();

        // Assert
        assertEquals(2, result.getExpenseList().size());
        assertEquals(2, result.getIncomeList().size());
        assertEquals("Groceries", result.getExpenseList().get(0).getTitle());
        assertEquals("Salary", result.getIncomeList().get(0).getTitle());
    }

    // Test for getStats method
    @Test
    public void testGetStats() {
        // Arrange
        when(incomeRepository.sumAllAmount()).thenReturn(1300.0);
        when(expenseRepository.sumAllAmount()).thenReturn(150.0);

        Income latestIncome = new Income(2L, "Freelance", "Freelance project", IncomeCategory.FREELANCE, LocalDate.of(2024, 11, 10), 300);
        Expense latestExpense = new Expense(2L, "Movies", "Movie night", ExpenseCategory.ENTERTAINMENT, LocalDate.of(2024, 11, 5), 50);

        when(incomeRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.of(latestIncome));
        when(expenseRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.of(latestExpense));

        List<Income> incomeList = List.of(
                new Income(1L, "Salary", "Monthly salary", IncomeCategory.SALARY, LocalDate.of(2024, 11, 2), 1000),
                new Income(2L, "Freelance", "Freelance project", IncomeCategory.FREELANCE, LocalDate.of(2024, 11, 10), 300)
        );

        List<Expense> expenseList = List.of(
                new Expense(1L, "Groceries", "Weekly groceries", ExpenseCategory.GROCERIES, LocalDate.of(2024, 11, 1), 100),
                new Expense(2L, "Movies", "Movie night", ExpenseCategory.ENTERTAINMENT, LocalDate.of(2024, 11, 5), 50)
        );

        when(incomeRepository.findAll()).thenReturn(incomeList);
        when(expenseRepository.findAll()).thenReturn(expenseList);

        // Act
        StatsDTO result = statsService.getStats();

        // Assert
        assertEquals(1300.0, result.getIncome());
        assertEquals(150.0, result.getExpense());
        assertEquals(1150.0, result.getBalance());
        assertEquals(300, result.getLatestIncome().getAmount());
        assertEquals(50, result.getLatestExpense().getAmount());
        assertEquals(1000.0, result.getMaxIncome());
        assertEquals(300.0, result.getMinIncome());
        assertEquals(100.0, result.getMaxExpense());
        assertEquals(50.0, result.getMinExpense());
    }

    // Test for getStats when there are no records
    @Test
    public void testGetStats_NoRecords() {
        // Arrange
        when(incomeRepository.sumAllAmount()).thenReturn(null);
        when(expenseRepository.sumAllAmount()).thenReturn(null);
        when(incomeRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.empty());
        when(expenseRepository.findFirstByOrderByDateDesc()).thenReturn(Optional.empty());
        when(incomeRepository.findAll()).thenReturn(List.of());
        when(expenseRepository.findAll()).thenReturn(List.of());

        // Act
        StatsDTO result = statsService.getStats();

        // Assert
        assertEquals(0.0, result.getIncome());
        assertEquals(0.0, result.getExpense());
        assertEquals(0.0, result.getBalance());
        assertNull(result.getLatestIncome());
        assertNull(result.getLatestExpense());
        assertNull(result.getMaxIncome());
        assertNull(result.getMinIncome());
        assertNull(result.getMaxExpense());
        assertNull(result.getMinExpense());
    }
}
