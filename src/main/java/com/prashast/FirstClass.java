package com.prashast;

import com.prashast.rest.SecuredRest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class FirstClass extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public FirstClass() {
        //root RESTful resource
        singletons.add(new SecuredRest());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
