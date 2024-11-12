package com.project.personalexpensetracker.entities;

import com.project.personalexpensetracker.dtos.IncomeDTO;
import com.project.personalexpensetracker.entities.enums.IncomeCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private IncomeCategory category;

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
