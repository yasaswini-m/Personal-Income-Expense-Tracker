package com.project.personalexpensetracker.dtos;

import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import lombok.Data;
import java.util.*;

@Data
public class GraphDTO {
    private List<Expense> expenseList;
    private List<Income> incomeList;
}
