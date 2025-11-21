package com.support.system.dto;

import com.support.system.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;

public class TicketUpdateStatusDTO {

    @NotNull
    private TicketStatus status;

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
