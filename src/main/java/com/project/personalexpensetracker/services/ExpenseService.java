package com.project.personalexpensetracker.services;

import com.project.personalexpensetracker.dtos.ExpenseDTO;
import com.project.personalexpensetracker.entities.Expense;

import java.util.List;

public interface ExpenseService {
    Expense postExpense(ExpenseDTO expenseDTO);
    List<ExpenseDTO> getAllExpenses();

    ExpenseDTO  getExpenseById(Long id);
    Expense updateExpense(Long id,ExpenseDTO expenseDTO);
    void deleteExpense(Long id);
}
