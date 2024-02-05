package ru.javarush.medov.controller.dto;

import lombok.Data;
import ru.javarush.medov.domain.Status;

@Data
public class TaskDTO {
    private String description;
    private Status status;
}
