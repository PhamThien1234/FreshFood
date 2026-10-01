package com.example.FreshFood.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubOrderStatus {
    @NotNull
    private Sub status;
}
