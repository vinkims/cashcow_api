package com.example.cashcow_api.dtos.cow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsCowNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.dtos.user.UserBasicDTO;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.models.ECowService;
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

    private Integer farmId;

    private FarmDTO farm;

    private Integer id;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @NotBlank
    @IsCowNameValid
    private String name;

    private LocalDate dateOfBirth;

    private String gender;

    private BreedDTO breed;

    private Integer breedId;

    private String color;

    private CowDTO parent;

    private Integer parentId;

    private String otherDetails;

    private @Valid CowProfileDTO profile;

    private List<CowServiceDTO> services = new ArrayList<>();

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    private Float weight;

    public CowDTO(ECow cow){
        setBreed(new BreedDTO(cow.getBreed()));
        setCalvesList(cow.getCalves());
        setCategory(cow.getCategory());
        setColor(cow.getColor());
        setCreatedOn(cow.getCreatedOn());
        setDateOfBirth(cow.getDateOfBirth());
        setGender(cow.getGender());
        setId(cow.getId());
        setFarm(new FarmDTO(cow.getFarm()));
        if (cow.getParent() != null){
            setParent(cow.getParent());
        }
        setName(cow.getName());
        setOtherDetails(cow.getOtherDetails());
        setServicesData(cow.getServices());
        setStatus(new StatusDTO(cow.getStatus()));
        setUpdatedOn(cow.getUpdatedOn());
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

    public void setServicesData(List<ECowService> servicesList){
        if (servicesList == null || servicesList.isEmpty()){ return; }
        for (ECowService cowService : servicesList){
            CowServiceDTO cowServiceDTO = new CowServiceDTO();
            cowServiceDTO.setCreatedOn(cowService.getCreatedOn());
            cowServiceDTO.setCowServiceType(cowService.getCowServiceType().getName());
            if (cowService.getResults() != null){
                cowServiceDTO.setResults(cowService.getResults());
            }
            if (cowService.getUser() != null){
                cowServiceDTO.setUser(new UserBasicDTO(cowService.getUser()));
            }

            services.add(cowServiceDTO);
        }
    }

}
