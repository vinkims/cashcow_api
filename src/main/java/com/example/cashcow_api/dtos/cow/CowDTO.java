package com.example.cashcow_api.dtos.cow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.cashcow_api.annotations.IsCowNameValid;
import com.example.cashcow_api.dtos.farm.FarmDTO;
import com.example.cashcow_api.dtos.status.StatusDTO;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowCategory;
import com.example.cashcow_api.models.ECowExpense;
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

    @JsonIgnoreProperties(value = {"createdOn", "updatedOn", "status"})
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

    private CowImageDTO image;

    private Integer imageId;

    private CowDTO parent;

    private Integer parentId;

    private String otherDetails;

    @JsonIgnoreProperties(value = {"bull", "calvingDate", "cow", "createdOn", "updatedOn", "status", "user"})
    private List<CowServiceDTO> services = new ArrayList<>();

    private List<CowExpenseDTO> cowExpenses;

    @JsonIgnoreProperties("description")
    private StatusDTO status;

    private Integer statusId;

    private Float weight;

    public CowDTO(ECow cow){
        setBreed(new BreedDTO(cow.getBreed()));
        setCalvesList(cow.getCalves());
        setCategory(cow.getCategory());
        setColor(cow.getColor());
        if (cow.getCowImage() != null) {
            setImage(new CowImageDTO(cow.getCowImage()));
        }
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
            calves.add(calfDTO);
        }
    }

    public void setCowExpensesData(List<ECowExpense> cowExpenseList) {
        if (cowExpenseList == null || cowExpenseList.isEmpty()) { return; }

        cowExpenses = new ArrayList<>();
        for (ECowExpense cowExpense : cowExpenseList) {
            cowExpenses.add(new CowExpenseDTO(cowExpense));
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
            services.add(new CowServiceDTO(cowService));
        }
    }

}
