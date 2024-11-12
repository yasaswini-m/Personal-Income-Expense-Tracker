package com.project.personalexpensetracker.entities;

import com.project.personalexpensetracker.dtos.ExpenseDTO;
import com.project.personalexpensetracker.dtos.IncomeDTO;
import com.project.personalexpensetracker.entities.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    private LocalDate date;
    private Integer amount;

    public ExpenseDTO getExpenseDto(){
        ExpenseDTO expenseDTO=new ExpenseDTO();

        expenseDTO.setId(id);
        expenseDTO.setDescription(description);
        expenseDTO.setTitle(title);
        expenseDTO.setAmount(amount);
        expenseDTO.setCategory(category);
        expenseDTO.setDate(date);

        return expenseDTO;
    }
}
