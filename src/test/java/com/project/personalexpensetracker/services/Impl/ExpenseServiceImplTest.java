package com.project.personalexpensetracker.services.Impl;

import com.project.personalexpensetracker.dtos.ExpenseDTO;
import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.enums.ExpenseCategory;
import com.project.personalexpensetracker.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    public void testPostExpense() {
        // Arrange
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setTitle("Movies");
        expenseDTO.setAmount(50);
        expenseDTO.setDescription("Spent on movies");
        expenseDTO.setCategory(ExpenseCategory.ENTERTAINMENT);
        expenseDTO.setDate(LocalDate.now());

        Expense savedExpense = new Expense();
        savedExpense.setId(1L);
        savedExpense.setTitle("Movies");
        savedExpense.setAmount(50);
        savedExpense.setDescription("Spent on movies");
        savedExpense.setCategory(ExpenseCategory.ENTERTAINMENT);
        savedExpense.setDate(LocalDate.now());

        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        // Act
        Expense result = expenseService.postExpense(expenseDTO);

        // Assert
        assertEquals("Movies", result.getTitle());
        assertEquals(50, result.getAmount());
        assertEquals("Spent on movies", result.getDescription());
        assertEquals(ExpenseCategory.ENTERTAINMENT, result.getCategory());
        assertEquals(expenseDTO.getDate(), result.getDate());

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void testGetAllExpenses() {
        // Arrange
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setTitle("Movies");
        expense1.setAmount(50);
        expense1.setDate(LocalDate.of(2024, 11, 10));

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setTitle("Groceries");
        expense2.setAmount(100);
        expense2.setDate(LocalDate.of(2024, 11, 11));

        when(expenseRepository.findAll()).thenReturn(List.of(expense1, expense2));

        // Act
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();

        // Assert
        assertEquals(2, expenses.size());
        assertEquals("Groceries", expenses.get(0).getTitle()); // Latest by date
        assertEquals("Movies", expenses.get(1).getTitle());
    }

    @Test
    public void testGetExpenseById_Found() {
        // Arrange
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Movies");
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        // Act
        ExpenseDTO result = expenseService.getExpenseById(1L);

        // Assert
        assertEquals("Movies", result.getTitle());
    }

    @Test
    public void testGetExpenseById_NotFound() {
        // Arrange
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> expenseService.getExpenseById(1L));
    }

    @Test
    public void testUpdateExpense_Found() {
        // Arrange
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setTitle("Updated Movies");
        expenseDTO.setAmount(60);
        expenseDTO.setDescription("Updated description");

        Expense existingExpense = new Expense();
        existingExpense.setId(1L);
        existingExpense.setTitle("Movies");

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(existingExpense);

        // Act
        Expense result = expenseService.updateExpense(1L, expenseDTO);

        // Assert
        assertEquals("Updated Movies", result.getTitle());
        assertEquals(60, result.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void testUpdateExpense_NotFound() {
        // Arrange
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setTitle("Updated Movies");
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> expenseService.updateExpense(1L, expenseDTO));
    }

    @Test
    public void testDeleteExpense_Found() {
        // Arrange
        Expense expense = new Expense();
        expense.setId(1L);
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        // Act
        expenseService.deleteExpense(1L);

        // Assert
        verify(expenseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteExpense_NotFound() {
        // Arrange
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> expenseService.deleteExpense(1L));
    }


}