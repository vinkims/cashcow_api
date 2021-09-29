package com.example.cashcow_api.dtos.cow;

import java.util.ArrayList;
import java.util.List;

import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class CowDTO {

    private List<CowDTO> calves = new ArrayList<>();

    private ECowCategory category;

    private CowProfileDTO profile;

    private Integer farmId;

    private FarmDTO farm;

    private Integer id;

    private String name;

    private String status;

    private Integer statusId;

    public CowDTO(ECow cow){
        setCalvesList(cow.getCalves());
        setCategory(cow.getCategory());
        setFarm(new FarmDTO(cow.getFarm()));
        setStatus(cow.getStatus().getName());
    }

    public void setCalvesList(List<ECow> calvesList){
        
        if (calvesList == null || calvesList.isEmpty()){ return; }
        
        for (ECow calf : calvesList){
            CowDTO calfDTO = new CowDTO();
            calfDTO.setCategory(calf.getCategory());
            calfDTO.setName(calf.getName());
            calfDTO.setProfile(new CowProfileDTO(calf.getProfile()));
            calves.add(calfDTO);
        }
    }

}
