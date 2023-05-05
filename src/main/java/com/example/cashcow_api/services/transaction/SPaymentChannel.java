package com.example.cashcow_api.services.transaction;

import java.util.List;
import java.util.Optional;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.transaction.PaymentChannelDTO;
import com.example.cashcow_api.exceptions.NotFoundException;
import com.example.cashcow_api.models.EPaymentChannel;
import com.example.cashcow_api.repositories.PaymentChannelDAO;
import com.example.cashcow_api.specifications.SpecBuilder;
import com.example.cashcow_api.specifications.SpecFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SPaymentChannel implements IPaymentChannel {

    @Autowired
    private PaymentChannelDAO paymentChannelDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EPaymentChannel create(PaymentChannelDTO paymentChannelDTO) {

        EPaymentChannel paymentChannel = new EPaymentChannel();
        paymentChannel.setDescription(paymentChannelDTO.getDescription());
        paymentChannel.setName(paymentChannelDTO.getName());

        save(paymentChannel);
        return paymentChannel;
    }

    @Override
    public Optional<EPaymentChannel> getById(Integer id) {
        return paymentChannelDAO.findById(id);
    }

    @Override
    public EPaymentChannel getById(Integer id, Boolean handleException) {
        Optional<EPaymentChannel> paymentChannel = getById(id);
        if (!paymentChannel.isPresent() && handleException) {
            throw new NotFoundException("payment channel with specified id not found", "paymentChannelId");
        }
        return paymentChannel.get();
    }

    @Override
    public List<EPaymentChannel> getList() {
        return paymentChannelDAO.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EPaymentChannel> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EPaymentChannel>) specFactory.generateSpecification(search, 
            specBuilder, allowedFields, "paymentChannel");

        Specification<EPaymentChannel> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return paymentChannelDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EPaymentChannel paymentChannel) {
        paymentChannelDAO.save(paymentChannel);
    }

    @Override
    public EPaymentChannel update(Integer id, PaymentChannelDTO paymentChannelDTO) {

        EPaymentChannel paymentChannel = getById(id, true);
        if (paymentChannelDTO.getDescription() != null) {
            paymentChannel.setDescription(paymentChannelDTO.getDescription());
        }
        if (paymentChannelDTO.getName() != null) {
            paymentChannel.setName(paymentChannelDTO.getName());
        }

        save(paymentChannel);
        return paymentChannel;
    }
    
}
