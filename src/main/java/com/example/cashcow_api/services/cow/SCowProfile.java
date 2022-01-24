package com.example.cashcow_api.services.cow;

import java.time.LocalDate;
import java.util.Optional;

import com.example.cashcow_api.dtos.cow.CowProfileDTO;
import com.example.cashcow_api.dtos.transaction.TransactionDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.ECow;
import com.example.cashcow_api.models.ECowImage;
import com.example.cashcow_api.models.ECowProfile;
import com.example.cashcow_api.repositories.CowProfileDAO;
import com.example.cashcow_api.services.transaction.ITransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SCowProfile {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Value(value = "${default.value.transaction-type.cow-purchase-type-id}")
    private Integer cowPurchaseTransactionTypeId;

    @Value(value = "${default.value.payment-channel.mpesa-channel-id}")
    private Integer mpesaPaymentChannelId;
    
    @Autowired
    private CowProfileDAO cowProfileDAO;

    @Autowired
    private ICowImage sCowImage;

    @Autowired
    private ITransaction sTransaction;

    public ECowProfile create(CowProfileDTO cowProfileDTO, ECow cow){

        ECowProfile profile = cow.getProfile() != null ? cow.getProfile() : new ECowProfile();
        profile.setCow(cow);
        setBreed(profile, cowProfileDTO);
        setColor(profile, cowProfileDTO);
        setDateOfBirth(profile, cowProfileDTO);
        setDateOfDeath(profile, cowProfileDTO);
        setDateOfPurchase(profile, cowProfileDTO);
        setDateOfDeath(profile, cowProfileDTO);
        setGender(profile, cowProfileDTO);
        setImage(profile, cowProfileDTO.getImageId());
        setLocationBought(profile, cowProfileDTO);
        setPurchaseAmount(profile, cowProfileDTO);
        setSaleAmount(profile, cowProfileDTO);

        if (cowProfileDTO.getDateOfPurchase() != null ){
            createPurchaseTransaction(
                cowProfileDTO.getPurchaseAmount(), 
                completeStatusId, 
                mpesaPaymentChannelId, 
                cowPurchaseTransactionTypeId,
                String.format("Cow id: %s", cow.getId())
            );
        }

        return profile;
    }

    /**
     * Create cow purchase transaction if cow was purchased
     * @param amount
     * @param statusId
     * @param paymentChannelId
     * @param transactionTypeId
     */
    public void createPurchaseTransaction(Float amount, Integer statusId, 
            Integer paymentChannelId, Integer transactionTypeId, String reference){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(amount);
        transactionDTO.setPaymentChannelId(paymentChannelId);
        transactionDTO.setReference(reference);
        transactionDTO.setStatusId(statusId);
        transactionDTO.setTransactionTypeId(transactionTypeId);
        sTransaction.create(transactionDTO);
    }

    public void save(ECowProfile cowProfile){
        cowProfileDAO.save(cowProfile);
    }

    public void setBreed(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getBreed() != null){
            cowProfile.setBreed(cowProfileDTO.getBreed());
        }
    }

    public void setColor(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getColor() != null){
            cowProfile.setColor(cowProfileDTO.getColor());
        }
    }

    public void setDateOfBirth(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfBirth() != null){
            cowProfile.setDateOfBirth(LocalDate.parse(cowProfileDTO.getDateOfBirth()));
        }
    }

    public void setDateOfDeath(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfDeath() != null){
            cowProfile.setDateOfDeath(LocalDate.parse(cowProfileDTO.getDateOfDeath()));
        }
    }

    public void setDateOfPurchase(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfPurchase() != null){
            cowProfile.setDateOfPurchase(LocalDate.parse(cowProfileDTO.getDateOfPurchase()));
        }
    }

    public void setDateOfSale(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getDateOfSale() != null){
            cowProfile.setDateOfSale(LocalDate.parse(cowProfileDTO.getDateOfSale()));
        }
    }

    public void setGender(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getGender() != null){
            cowProfile.setGender(cowProfileDTO.getGender());
        }
    }

    public void setImage(ECowProfile cowProfile, Integer imageId){
        
        if (imageId != null){
            Optional<ECowImage> image = sCowImage.getById(imageId);
            if (!image.isPresent()){
                throw new NotFoundException("image with specified id not found", "imageId");
            }
            cowProfile.setCowImage(image.get());
        }
    }

    public void setLocationBought(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getLocationBought() != null){
            cowProfile.setLocationBought(cowProfileDTO.getLocationBought());
        }
    }

    public void setPurchaseAmount(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getPurchaseAmount() != null){
            cowProfile.setPurchaseAmount(cowProfileDTO.getPurchaseAmount());
        }
    }

    public void setSaleAmount(ECowProfile cowProfile, CowProfileDTO cowProfileDTO){
        if (cowProfileDTO.getSaleAmount() != null){
            cowProfile.setSaleAmount(cowProfileDTO.getSaleAmount());
        }
    }
}

