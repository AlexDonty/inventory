package com.exam.inventory.common.constans;

public enum StatusEnum {

    AVAILABLE("Disponible"),
    ASSIGNED("Asignado"),
    MAINTENANCE("En Mantenimiento"),
    REMOVED("Baja");

    StatusEnum(String status) {
        this.status = status;
    }

    private String status;

}
