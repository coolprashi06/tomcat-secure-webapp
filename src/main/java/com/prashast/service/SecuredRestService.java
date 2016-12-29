package com.prashast.service;

import org.springframework.stereotype.Service;

@Service
public class SecuredRestService {

    public String concatenate(String firstName, String lastName){
        return firstName + " " + lastName;
    }
}
