package com.example.cashcow_api.services.shop;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.shop.ShopDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EFarm;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.repositories.ShopDAO;
import com.example.cashcow_api.services.farm.IFarm;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SShop implements IShop{

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired 
    private IFarm sFarm;

    @Autowired
    private IStatus sStatus;
    
    @Autowired 
    private ShopDAO shopDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String shopName){
        return shopDAO.existsByName(shopName);
    }

    @Override
    public EShop create(ShopDTO shopDTO){

        EShop shop = new EShop();
        shop.setCreatedOn(LocalDateTime.now());
        shop.setLocation(shopDTO.getLocation());
        if (shopDTO.getName() != null){
            shop.setName(shopDTO.getName().toUpperCase());
        }
        setFarm(shop, shopDTO.getFarmId());
        Integer statusId = shopDTO.getStatusId() == null ? activeStatusId : shopDTO.getStatusId();
        setStatus(shop, statusId); 

        save(shop);
        return shop;
    }

    @Override
    public List<EShop> getAll(){
        return shopDAO.findAll();
    }

    @Override
    public Optional<EShop> getById(Integer shopId){
        return shopDAO.findById(shopId);
    }

    @Override
    public EShop getById(Integer shopId, Boolean handleException) {
        Optional<EShop> shop = getById(shopId);
        if (!shop.isPresent() && handleException) {
            throw new NotFoundException("shop with specified id not found", "shopId");
        }
        return shop.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EShop> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EShop> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EShop>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "shop");

        Specification<EShop> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return shopDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EShop shop){
        shopDAO.save(shop);
    }

    public void setFarm(EShop shop, Integer farmId){
        if (farmId == null){ return; }

        EFarm farm = sFarm.getById(farmId, true);
        shop.setFarm(farm);
    }

    public void setStatus(EShop shop, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        shop.setStatus(status);
    }

    @Override
    public EShop update(Integer shopId, ShopDTO shopDTO) {

        EShop shop = getById(shopId, true);
        if (shopDTO.getLocation() != null) {
            shop.setLocation(shopDTO.getLocation());
        }
        if (shopDTO.getName() != null) {
            shop.setName(shopDTO.getName().toUpperCase());
        }
        shop.setUpdatedOn(LocalDateTime.now());
        setFarm(shop, shopDTO.getFarmId());
        setStatus(shop, shopDTO.getStatusId());

        save(shop);
        return shop;
    }
}
