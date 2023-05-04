package com.example.cashcow_api.controllers;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.cashcow_api.dtos.general.PageDTO;
import com.example.cashcow_api.dtos.user.UserDTO;
import com.example.cashcow_api.models.EUser;
import com.example.cashcow_api.responses.SuccessPaginatedResponse;
import com.example.cashcow_api.responses.SuccessResponse;
import com.example.cashcow_api.services.user.IUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class CUser {
    
    @Autowired
    private IUser sUser;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException{

        EUser user = sUser.create(userDTO);

        return ResponseEntity
            .created(new URI("/" + user.getId()))
            .body(new SuccessResponse(201, "successfully created user", new UserDTO(user)));
    }

    /**
     * Get a paginated list of users
     * @param params
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaginatedUserList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        PageDTO pageDTO = new PageDTO(params);

        ArrayList<String> allowableFields = new ArrayList<>(
            Arrays.asList("role.id", "role.name", "shop.id", "status.id", "status.name", "farm.id"));

        Page<EUser> userPage = sUser.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200,  "fetched user list", userPage, UserDTO.class, EUser.class));
    }

    @GetMapping(path = "/system", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getSystemUsers() throws 
            InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException{

        List<EUser> systemUsers = sUser.getSystemUsers();

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "fetched system users list", systemUsers, UserDTO.class, EUser.class));
    }

    /**
     * Get a user by id or contact value
     * @param userValue
     * @return
     */
    @GetMapping(path = "/{userValue}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getUser(@PathVariable(name = "userValue") String userValue){

        EUser user = sUser.getByIdOrContact(userValue, true);
        
        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "user fetched", new UserDTO(user)));
    }

    @PatchMapping(path = "/{userValue}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateUser(@PathVariable String userValue, @Valid @RequestBody UserDTO userDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException{

        EUser user = sUser.update(userValue, userDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(202, "successfully updated user", new UserDTO(user)));
    }
}
