package com.project.personalexpensetracker.services.Impl;

import com.project.personalexpensetracker.dtos.GraphDTO;
import com.project.personalexpensetracker.dtos.StatsDTO;
import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import com.project.personalexpensetracker.repositories.ExpenseRepository;
import com.project.personalexpensetracker.repositories.IncomeRepository;
import com.project.personalexpensetracker.services.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    public GraphDTO getChartData(){
        LocalDate endDate= LocalDate.now();
        LocalDate startDate=endDate.minusDays(27);
        GraphDTO graphDTO=new GraphDTO();
        graphDTO.setExpenseList(expenseRepository.findByDateBetween(startDate,endDate));
        graphDTO.setIncomeList(incomeRepository.findByDateBetween(startDate,endDate));

        return graphDTO;
    }

    @Override
    public StatsDTO getStats(){
        Double totalIncome=incomeRepository.sumAllAmount();
        Double totalExpense=expenseRepository.sumAllAmount();

        totalIncome = (totalIncome != null) ? totalIncome : 0.0;
        totalExpense = (totalExpense != null) ? totalExpense : 0.0;

        Optional<Income> optionalIncome=incomeRepository.findFirstByOrderByDateDesc();
        Optional<Expense> optionalExpense=expenseRepository.findFirstByOrderByDateDesc();

        StatsDTO statsDTO=new StatsDTO();
        statsDTO.setExpense(totalExpense);
        statsDTO.setIncome(totalIncome);

        optionalExpense.ifPresent(statsDTO::setLatestExpense);
        optionalIncome.ifPresent(statsDTO::setLatestIncome);

        statsDTO.setBalance(totalIncome-totalExpense);

        List<Income> incomeList=incomeRepository.findAll();
        List<Expense> expenseList=expenseRepository.findAll();

        OptionalDouble minIncome=incomeList.stream().mapToDouble(Income::getAmount).min();
        OptionalDouble maxIncome=incomeList.stream().mapToDouble(Income::getAmount).max();

        OptionalDouble minExpense=expenseList.stream().mapToDouble(Expense::getAmount).min();
        OptionalDouble maxExpense=expenseList.stream().mapToDouble(Expense::getAmount).max();

        statsDTO.setMinIncome(minIncome.isPresent() ? minIncome.getAsDouble() : null);
        statsDTO.setMaxIncome(maxIncome.isPresent() ? maxIncome.getAsDouble() : null);

        statsDTO.setMinExpense(minExpense.isPresent() ? minExpense.getAsDouble() : null);
        statsDTO.setMaxExpense(maxExpense.isPresent() ? maxExpense.getAsDouble() : null);

        return statsDTO;
    }

}
