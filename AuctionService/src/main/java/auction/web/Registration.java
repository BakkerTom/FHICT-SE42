package auction.web;

import auction.domain.User;
import auction.service.RegistrationMgr;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Stijn
 */
@WebService
public class Registration {
    private RegistrationMgr registrationMgr = new RegistrationMgr();
    
    public User registerUser(String email) {
        return registrationMgr.registerUser(email);
    }
    
    public User getUser(String email) {
        return registrationMgr.getUser(email);
    }
    
    public List<User> getUsers() {
        return registrationMgr.getUsers();
    }
}
