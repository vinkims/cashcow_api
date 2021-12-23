package com.example.cashcow_api.utils.enums;

import java.util.stream.Stream;

public enum Gender {
    
    M("male"), F("female");

    private String gender;

    private Gender(String gender){
        this.gender = gender;
    }

    public String getGender(){
        return this.gender;
    }

    public static Gender of(String gender){
        return Stream.of(Gender.values())
            .filter(p -> p.getGender().equals(gender))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
