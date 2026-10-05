package com.example.FreshFood.dto.request;

import com.example.FreshFood.enums.SubOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubOrderStatusRequest {

    @NotNull
    private SubOrderStatus status;
}
