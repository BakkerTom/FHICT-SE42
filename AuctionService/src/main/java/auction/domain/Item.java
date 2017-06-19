package auction.domain;

import javax.jws.WebMethod;
import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.count", query = "select count(i) from Item as i"),
        @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :id"),
        @NamedQuery(name = "Item.findAll", query = "select i from Item as i"),
        @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Comparable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "category"))
    })
    private Category category;

    private String description;

    @OneToOne
    private Bid highest;

    public Item(){}

    /**
     * Create a new a Item object.
     * @param seller Seller (User) that sells this item.
     * @param category Category this items belongs to.
     * @param description Description associated with this item.
     */
    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;

        //newBid(seller, new Money(0, Money.EURO));
    }

    /**
     * Get ID from this item.
     * @return Long ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the seller from this item.
     * @return Seller object.
     */
    public User getSeller() {
        return seller;
    }

    /**
     * Get the category associated with this item.
     * @return Category object.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Get the description associated with this item.
     * @return String description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the highest bid.
     * @return Bid object.
     */
    public Bid getHighestBid() {
        return highest;
    }

    /**
     * Make a bid on this item.
     * @param buyer User object representing the buyer.
     * @param amount Money object representing the amount of money that is bid.
     * @return When the current bid is higher than the highest bid at the moment, the highest bid will be overridden and the current bid is returned.
     * When the current bid is lower than Null is returned.
     */
    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }
}
