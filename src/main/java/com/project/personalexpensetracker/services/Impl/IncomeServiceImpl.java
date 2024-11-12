package com.project.personalexpensetracker.services.Impl;

import com.project.personalexpensetracker.dtos.ExpenseDTO;
import com.project.personalexpensetracker.dtos.IncomeDTO;
import com.project.personalexpensetracker.entities.Expense;
import com.project.personalexpensetracker.entities.Income;
import com.project.personalexpensetracker.repositories.IncomeRepository;
import com.project.personalexpensetracker.services.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    public Income postIncome(IncomeDTO incomeDTO){
        return saveOrUpdateIncome(new Income(),incomeDTO);
    }

    public Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO){
        income.setTitle(incomeDTO.getTitle());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        income.setCategory(incomeDTO.getCategory());
        income.setDescription(incomeDTO.getDescription());
        return incomeRepository.save(income);
    }

    @Override
    public List<IncomeDTO> getAllIncome() {
        return incomeRepository.findAll().stream()
                .sorted(Comparator.comparing(Income::getDate).reversed())
                .map(Income::getIncomeDto)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeDTO getIncomeById(Long id) {
        Optional<Income> optionalIncome=incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return optionalIncome.get().getIncomeDto();
        }else{
            throw new EntityNotFoundException("Expense does not exist with the id"+id);
        }
    }

    @Override
    public Income updateIncome(Long id, IncomeDTO incomeDTO) {
        Optional<Income> optionalIncome=incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return saveOrUpdateIncome(optionalIncome.get(),incomeDTO);
        }else{
            throw new EntityNotFoundException("Expense is not present with id"+id);
        }
    }

    @Override
    public void deleteIncome(Long id) {
        Optional<Income> optionalIncome=incomeRepository.findById(id);
        if(optionalIncome.isPresent()) {
            incomeRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Expense is not present with id"+id);
        }
    }


}
