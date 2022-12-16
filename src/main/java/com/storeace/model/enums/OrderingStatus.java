package com.storeace.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    ALL("Все"),
    WAITING("Ожидание"),
    RESERVED("Зарезервировано"),
    NOT_RESERVED("Не зарезервировано"),
    SHIPMENT("В отгрузке"),
    SHIPPED("Отгружено");

    private final String name;
}
