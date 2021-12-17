package com.example.cashcow_api.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummaryUserDTO {
    
    private Long count;

    private String role;

    public SummaryUserDTO(Long count, String role){
        setCount(count);
        setRole(role);
    }
}
