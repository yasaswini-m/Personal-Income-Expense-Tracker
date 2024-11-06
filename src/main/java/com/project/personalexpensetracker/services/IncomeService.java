package com.project.personalexpensetracker.services;

import com.project.personalexpensetracker.dtos.ExpenseDTO;
import com.project.personalexpensetracker.dtos.IncomeDTO;
import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;

import java.util.List;

public interface IncomeService {

    public Income saveOrUpdateIncome(IncomeDTO incomeDTO);
    List<IncomeDTO> getAllIncome();
    IncomeDTO getIncomeById(Long id);
    Income updateIncome(Long id,IncomeDTO incomeDTO);
    void deleteIncome(Long id);
}
