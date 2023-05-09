package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkShopDeliveryDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkShopDelivery;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EStatus;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkShopDeliveryDAO;
import com.example.cashcow_api.services.shop.IShop;
import com.example.cashcow_api.services.status.IStatus;
import com.example.cashcow_api.services.transaction.ITransaction;
import com.example.cashcow_api.services.user.IUser;
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
public class SMilkShopDelivery implements IMilkShopDelivery {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private IShop sShop;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransaction sTransaction;

    @Autowired
    private IUser sUser;

    @Autowired
    private MilkShopDeliveryDAO milkDeliveryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Value(value = "${default.value.transaction-type.milk-transport-type-id}")
    private Integer milkTransportTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Override
    public EMilkShopDelivery create(MilkShopDeliveryDTO deliveryDTO) {
        EMilkShopDelivery delivery = new EMilkShopDelivery();
        delivery.setCreatedOn(LocalDateTime.now());
        delivery.setQuantity(deliveryDTO.getQuantity());
        setShop(delivery, deliveryDTO.getShopId());
        Integer statusId = deliveryDTO.getStatusId() == null ? completeStatusId : deliveryDTO.getStatusId();
        setStatus(delivery, statusId);
        setUser(delivery, deliveryDTO.getUserId());

        save(delivery);

        // if (deliveryDTO.getTransportCost() != null && deliveryDTO.getTransportCost() != 0){
            // createTransaction(
            //     deliveryDTO.getTransportCost(), 
            //     deliveryDTO.getUserId(), 
            //     deliveryDTO.getShopId(), 
            //     mpesaPaymentChannelId, 
            //     milkTransportTypeId,
            //     delivery.getId()
            // );
        // }

        return delivery;
    }

    // public void createTransaction(Float amount, Integer attendantId, Integer shopId, 
    //         Integer paymentChannelId, Integer transactionTypeId, Integer deliveryId){

    //     TransactionDTO transactionDTO = new TransactionDTO();
    //     transactionDTO.setAmount(amount);
    //     transactionDTO.setAttendantId(attendantId);
    //     transactionDTO.setShopId(shopId);
    //     transactionDTO.setPaymentChannelId(paymentChannelId);
    //     transactionDTO.setTransactionTypeId(transactionTypeId);
    //     transactionDTO.setReference(String.format("Delivery id: %s", deliveryId));

    //     sTransaction.create(transactionDTO);
    // }

    @Override
    public Optional<EMilkShopDelivery> getById(Integer deliveryId) {
        return milkDeliveryDAO.findById(deliveryId);
    }

    @Override
    public EMilkShopDelivery getById(Integer deliveryId, Boolean handleException) {
        Optional<EMilkShopDelivery> delivery = getById(deliveryId);
        if (!delivery.isPresent() && handleException) {
            throw new NotFoundException("milk shop delivery with specified id not found", "milkShopDeliveryId");
        }
        return delivery.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkShopDelivery> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EMilkShopDelivery> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EMilkShopDelivery>) specFactory.generateSpecification(search, 
            specBuilder, allowableFields, "milkDelivery");

        Specification<EMilkShopDelivery> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return milkDeliveryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkShopDelivery delivery) {
        milkDeliveryDAO.save(delivery);
    }

    public void setShop(EMilkShopDelivery delivery, Integer shopId){
        if (shopId == null){ return; }
        
        EShop shop = sShop.getById(shopId, true);
        delivery.setShop(shop);
    }

    public void setStatus(EMilkShopDelivery delivery, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        delivery.setStatus(status);
    }

    public void setUser(EMilkShopDelivery delivery, Integer userId){
        if (userId == null){ return; }

        EUser user = sUser.getById(userId, true);
        delivery.setUser(user);
    }

    @Override
    public EMilkShopDelivery update(Integer deliveryId, MilkShopDeliveryDTO deliveryDTO) {

        EMilkShopDelivery delivery = getById(deliveryId, true);
        if (deliveryDTO.getQuantity() != null) {
            delivery.setQuantity(deliveryDTO.getQuantity());
        }
        delivery.setUpdatedOn(LocalDateTime.now());
        setShop(delivery, deliveryDTO.getShopId());
        setStatus(delivery, deliveryDTO.getStatusId());
        setUser(delivery, deliveryDTO.getUserId());

        save(delivery);
        return delivery;
    }
    
}

// TODO: Create expense