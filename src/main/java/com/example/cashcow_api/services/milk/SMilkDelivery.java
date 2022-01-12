package com.example.cashcow_api.services.milk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.milk.MilkDeliveryDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EMilkDelivery;
import com.example.cashcow_api.models.EShop;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.repositories.MilkDeliveryDAO;
import com.example.cashcow_api.services.shop.IShop;
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
public class SMilkDelivery implements IMilkDelivery {

    @Autowired
    private IShop sShop;

    @Autowired
    private ITransaction sTransaction;

    @Autowired
    private IUser sUser;

    @Autowired
    private MilkDeliveryDAO milkDeliveryDAO;

    @Autowired
    private SpecFactory specFactory;

    @Value(value = "${default.value.transaction-type.milk-transport-type-id}")
    private Integer milkTransportTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;

    @Override
    public EMilkDelivery create(MilkDeliveryDTO deliveryDTO) {
        EMilkDelivery delivery = new EMilkDelivery();
        delivery.setCreatedOn(LocalDateTime.now());
        delivery.setQuantity(deliveryDTO.getQuantity());
        setShop(delivery, deliveryDTO.getShopId());
        setUser(delivery, deliveryDTO.getUserId());

        save(delivery);

        if (deliveryDTO.getTransportCost() != null && deliveryDTO.getTransportCost() != 0){
            createTransaction(
                deliveryDTO.getTransportCost(), 
                deliveryDTO.getUserId(), 
                deliveryDTO.getShopId(), 
                mpesaPaymentChannelId, 
                milkTransportTypeId,
                delivery.getId()
            );
        }

        return delivery;
    }

    public void createTransaction(Float amount, Integer attendantId, Integer shopId, 
            Integer paymentChannelId, Integer transactionTypeId, Integer deliveryId){

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setAttendantId(attendantId);
        transactionDTO.setShopId(shopId);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setTransactionTypeId(transactionTypeId);
        transactionDTO.setReference(String.format("Delivery id: %s", deliveryId));

        sTransaction.create(transactionDTO);
    }

    @Override
    public Optional<EMilkDelivery> getById(Integer deliveryId) {
        return milkDeliveryDAO.findById(deliveryId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<EMilkDelivery> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EMilkDelivery> specBuilder = new SpecBuilder<>();
        specBuilder = (SpecBuilder<EMilkDelivery>) specFactory.generateSpecification(search, specBuilder, allowableFields, "milkDelivery");
        Specification<EMilkDelivery> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));
        
        return milkDeliveryDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EMilkDelivery delivery) {
        milkDeliveryDAO.save(delivery);
    }

    public void setShop(EMilkDelivery delivery, Integer shopId){
        if (shopId == null){ return; }
        Optional<EShop> shop = sShop.getById(shopId);
        if (!shop.isPresent()){
            throw new NotFoundException("shop with specified id not found", "shopId");
        }
        delivery.setShop(shop.get());
    }

    public void setUser(EMilkDelivery delivery, Integer userId){
        if (userId == null){ return; }
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()){
            throw new NotFoundException("user with specified id not found", "userId");
        }
        delivery.setUser(user.get());
    }
    
}
