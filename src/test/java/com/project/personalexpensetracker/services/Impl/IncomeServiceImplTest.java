package com.project.personalexpensetracker.services.Impl;

import com.project.personalexpensetracker.dtos.IncomeDTO;
import com.project.personalexpensetracker.entities.Income;
import com.project.personalexpensetracker.entities.enums.IncomeCategory;
import com.project.personalexpensetracker.repositories.IncomeRepository;
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
public class IncomeServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    @Test
    public void testPostIncome() {
        // Arrange
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTitle("Salary");
        incomeDTO.setAmount(1000);
        incomeDTO.setDescription("Monthly salary");
        incomeDTO.setCategory(IncomeCategory.SALARY);
        incomeDTO.setDate(LocalDate.now());

        Income savedIncome = new Income();
        savedIncome.setId(1L);
        savedIncome.setTitle("Salary");
        savedIncome.setAmount(1000);
        savedIncome.setDescription("Monthly salary");
        savedIncome.setCategory(IncomeCategory.SALARY);
        savedIncome.setDate(LocalDate.now());

        when(incomeRepository.save(any(Income.class))).thenReturn(savedIncome);

        // Act
        Income result = incomeService.postIncome(incomeDTO);

        // Assert
        assertEquals("Salary", result.getTitle());
        assertEquals(1000, result.getAmount());
        assertEquals("Monthly salary", result.getDescription());
        assertEquals(IncomeCategory.SALARY, result.getCategory());
        assertEquals(incomeDTO.getDate(), result.getDate());

        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    public void testGetAllIncome() {
        // Arrange
        Income income1 = new Income();
        income1.setId(1L);
        income1.setTitle("Salary");
        income1.setAmount(1000);
        income1.setDate(LocalDate.of(2024, 11, 10));

        Income income2 = new Income();
        income2.setId(2L);
        income2.setTitle("Freelance");
        income2.setAmount(500);
        income2.setDate(LocalDate.of(2024, 11, 11));

        when(incomeRepository.findAll()).thenReturn(List.of(income1, income2));

        // Act
        List<IncomeDTO> incomeList = incomeService.getAllIncome();

        // Assert
        assertEquals(2, incomeList.size());
        assertEquals("Freelance", incomeList.get(0).getTitle()); // Should be sorted by date
        assertEquals("Salary", incomeList.get(1).getTitle());
    }

    @Test
    public void testGetIncomeById_Found() {
        // Arrange
        Income income = new Income();
        income.setId(1L);
        income.setTitle("Salary");
        when(incomeRepository.findById(1L)).thenReturn(Optional.of(income));

        // Act
        IncomeDTO result = incomeService.getIncomeById(1L);

        // Assert
        assertEquals("Salary", result.getTitle());
    }

    @Test
    public void testGetIncomeById_NotFound() {
        // Arrange
        when(incomeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> incomeService.getIncomeById(1L));
    }

    @Test
    public void testUpdateIncome_Found() {
        // Arrange
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTitle("Updated Salary");
        incomeDTO.setAmount(1200);
        incomeDTO.setDescription("Updated monthly salary");

        Income existingIncome = new Income();
        existingIncome.setId(1L);
        existingIncome.setTitle("Salary");

        when(incomeRepository.findById(1L)).thenReturn(Optional.of(existingIncome));
        when(incomeRepository.save(any(Income.class))).thenReturn(existingIncome);

        // Act
        Income result = incomeService.updateIncome(1L, incomeDTO);

        // Assert
        assertEquals("Updated Salary", result.getTitle());
        assertEquals(1200, result.getAmount());
        assertEquals("Updated monthly salary", result.getDescription());
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    public void testUpdateIncome_NotFound() {
        // Arrange
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setTitle("Updated Salary");
        when(incomeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> incomeService.updateIncome(1L, incomeDTO));
    }

    @Test
    public void testDeleteIncome_Found() {
        // Arrange
        Income income = new Income();
        income.setId(1L);
        when(incomeRepository.findById(1L)).thenReturn(Optional.of(income));

        // Act
        incomeService.deleteIncome(1L);

        // Assert
        verify(incomeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteIncome_NotFound() {
        // Arrange
        when(incomeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> incomeService.deleteIncome(1L));
    }
}
