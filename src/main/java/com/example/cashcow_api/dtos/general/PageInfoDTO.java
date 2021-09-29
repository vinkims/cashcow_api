package com.example.cashcow_api.dtos.general;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageInfoDTO {    

    private String nextPage;

    private Integer pageNumber;

    private Integer pageResults;

    private Integer pageSize;

    private String prevPage;

    private Integer totalPages;

    private Long totalResults;

    public PageInfoDTO(Page<?> page){
        this.setPageNumber(page.getPageable().getPageNumber());
        this.setPageResults(page.getNumberOfElements());
        this.setPageSize(page.getPageable().getPageSize());
        this.setTotalPages(page.getTotalPages());
        this.setTotalResults(page.getTotalElements());
    }
}
