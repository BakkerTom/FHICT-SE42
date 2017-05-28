package auction.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.getAll", query = "select u from User as u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :email"),
    @NamedQuery(name = "User.getOfferedItems", query = "select offeredItems from User")
})
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(unique = true)
    private String email;

    @OneToMany
    private Set<Item> offeredItems;

    public User() {
        this.offeredItems = new HashSet<>();
    }

    /**
     * Create new User object.
     * @param email Email address that is provided by the user.
     */
    public User(String email) {
        this.email = email;
        this.offeredItems = new HashSet<>();
    }

    /**
     * Get email address.
     * @return String email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get Iterator for offered items.
     * @return Iterator offered items.
     */
    public Iterator<Item> getOfferedItems() { return this.offeredItems.iterator(); }

    /**
     * Get number of offered items.
     * @return Number of offered items.
     */
    public int numberOfOfferedItems() { return this.offeredItems.size(); }

    /**
     * Add item to offered items.
     * @param item Item object.
     */
    public void addItem(Item item) {
        this.offeredItems.add(item);
    }
}
