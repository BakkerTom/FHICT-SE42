package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.getAll", query = "select u from User as u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email")
})
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(unique = true)
    private String email;

    public User() {}

    /**
     * Create new User object.
     * @param email Email address that is provided by the user.
     */
    public User(String email) {
        this.email = email;

    }

    /**
     * Get email address.
     * @return String email address.
     */
    public String getEmail() {
        return email;
    }
}
