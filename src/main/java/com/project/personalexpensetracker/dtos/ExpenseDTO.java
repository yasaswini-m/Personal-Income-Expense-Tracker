package com.project.personalexpensetracker.dtos;

import com.project.personalexpensetracker.entities.enums.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseDTO {
    private Long id;

    @NotBlank(message = "Title is required.")
    private String title;

    private String description;

    @NotNull(message = "Category is required.")
    private ExpenseCategory category;

    @NotNull(message = "Date is required.")
    private LocalDate date;

    @NotNull(message = "Amount is required.")
    private Integer amount;
}
