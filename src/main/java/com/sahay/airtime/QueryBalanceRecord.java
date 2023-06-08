package com.sahay.airtime;

import java.util.List;

public record QueryBalanceRecord(String type, List<Id> id, String password) {
    public record Id(String value, String schemeName) {
    }
}
