package com.project.personalexpensetracker.entities;

import com.project.personalexpensetracker.dtos.IncomeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private LocalDate date;
    private Integer amount;

    public IncomeDTO getIncomeDto(){
        IncomeDTO incomeDTO=new IncomeDTO();

        incomeDTO.setId(id);
        incomeDTO.setDescription(description);
        incomeDTO.setTitle(title);
        incomeDTO.setAmount(amount);
        incomeDTO.setCategory(category);
        incomeDTO.setDate(date);

        return incomeDTO;
    }
}
