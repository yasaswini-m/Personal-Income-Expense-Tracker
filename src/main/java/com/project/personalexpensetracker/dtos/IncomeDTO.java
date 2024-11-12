package com.project.personalexpensetracker.dtos;

import com.project.personalexpensetracker.entities.enums.IncomeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeDTO {
    private Long id;
    @NotBlank(message = "Title is required.")
    private String title;

    private String description;

    @NotNull(message = "Category is required.")
    private IncomeCategory category;

    @NotNull(message = "Date is required.")
    private LocalDate date;

    @NotNull(message = "Amount is required.")
    private Integer amount;
}
