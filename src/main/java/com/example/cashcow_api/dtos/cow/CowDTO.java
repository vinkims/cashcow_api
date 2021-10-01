package com.example.cashcow_api.dtos.cow;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsCowNameValid;
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

    private Integer categoryId;

    private CowProfileDTO profile;

    private Integer farmId;

    private FarmDTO farm;

    private Integer id;

    @NotBlank
    @IsCowNameValid
    private String name;

    private CowDTO parent;

    private Integer parentId;

    private String status;

    private Integer statusId;

    public CowDTO(ECow cow){
        setCalvesList(cow.getCalves());
        setCategory(cow.getCategory());
        setId(cow.getId());
        setFarm(new FarmDTO(cow.getFarm()));
        setParent(cow.getParent());
        setProfile(new CowProfileDTO(cow.getProfile()));
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

    public void setParent(ECow cow){
        CowDTO calfParent = new CowDTO();
        calfParent.setName(cow.getName());
        calfParent.setId(cow.getId());
        this.parent = calfParent;
    }

}
