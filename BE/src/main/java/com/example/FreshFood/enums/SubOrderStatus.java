package com.example.FreshFood.enums;

public enum SubOrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPING,
    COMPLETED,
    CANCELLED;

    public boolean canTransitionTo(SubOrderStatus next){
        return switch (this){
            case PENDING ->
                next == CONFIRMED || next == CANCELLED;
            case CONFIRMED ->
                next == PROCESSING;
            case  PROCESSING ->
                next == SHIPPING;
            case SHIPPING ->
                next == COMPLETED;

            case COMPLETED, CANCELLED -> false;

        };
    }
}
