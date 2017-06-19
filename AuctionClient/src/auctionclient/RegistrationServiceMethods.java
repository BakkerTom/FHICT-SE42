/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

import auction.web.RegistrationService;
import auction.web.User;
import java.util.List;


/**
 *
 * @author Stijn
 */
public class RegistrationServiceMethods {
    private static final RegistrationService service = new RegistrationService();
    
    public RegistrationServiceMethods(){}
    
    public User registerUser(String email) {
        auction.web.Registration port = service.getRegistrationPort();
        return port.registerUser(email);
    }
    
    public User getUser(String email) {
        auction.web.Registration port = service.getRegistrationPort();
        return port.getUser(email);
    }
    
    public List<User> getUsers() {
        auction.web.Registration port = service.getRegistrationPort();
        return port.getUsers();
    }
}
