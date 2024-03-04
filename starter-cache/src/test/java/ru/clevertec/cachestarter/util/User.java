package ru.clevertec.cachestarter.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private LocalDate birthdate;
    private Boolean active;



}
