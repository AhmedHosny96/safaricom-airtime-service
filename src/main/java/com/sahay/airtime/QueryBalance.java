package com.sahay.airtime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBalance {

    private String type;
    private List<Id> id;
    private String password;

    public static class Id {

        private String type;
        private String value;

        public Id(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
