package com.support.system.dto;

import com.support.system.enums.Priority;
import jakarta.validation.constraints.NotBlank;

public class TicketCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Priority priority = Priority.MEDIUM;

    // getters e setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
