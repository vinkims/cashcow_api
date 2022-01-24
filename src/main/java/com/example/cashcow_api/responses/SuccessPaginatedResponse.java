package com.example.cashcow_api.responses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cashcow_api.dtos.general.PageInfoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = { "contentMap" })
public class SuccessPaginatedResponse {

    private Object content;

    private String message;

    private int status = 200;

    Map<String, Object> contentMap = new HashMap<>();

    public SuccessPaginatedResponse(int status, String message, Page<?> page){
        contentMap.put("data", page.getContent());
        setBasicProperties(status, message, page);
        this.setContent(contentMap);
    }

    public SuccessPaginatedResponse(int status, String message, List<?> dataArray, Class<?> customClass, Class<?> entityClass) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{
        
        this.setMessage(message);
        this.setStatus(status);

        List<Object> contentList = new ArrayList<>();
        for (Object objectItem : dataArray){
            Object customObj = customClass.getConstructor(entityClass).newInstance(objectItem);
            contentList.add(customObj);
        }
        contentMap.put("data", contentList);
        this.setContent(contentMap);
    }

    /**
     * Handles paginated responses that require transformation
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public SuccessPaginatedResponse(int status, String message, Page<?> page, Class<?> customClass, Class<?> entityClass) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        setBasicProperties(status, message, page);

        List<Object> contentList = new ArrayList<>();
        for (Object obj : page.getContent()){
            Object customObj = customClass.getConstructor(entityClass).newInstance(obj);
            contentList.add(customObj);
        }
        contentMap.put("data", contentList);
        this.setContent(contentMap);
    }

    /**
     * Abstracts setting of basic SuccessPaginatedResponse props
     */
    public void setBasicProperties(int status, String message, Page<?> page){
        contentMap.put("pageInfo", new PageInfoDTO(page));
        this.setMessage(message);
        this.setStatus(status);
    }
    
}
