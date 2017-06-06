package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FontysTime time;

    @ManyToOne
    private User buyer;
    private Money amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = true)
    private Item item;

    public Bid(){};

    /**
     * Create a new Bid object with current time.
     * @param buyer User object that places the bid.
     * @param amount Amount of money.
     */
    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
        this.time = FontysTime.now();
    }

    public FontysTime getTime() {
        return time;
    }

    @OneToOne
    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() { return this.item; }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Bid.class) return false;

        Bid compare = (Bid) obj;

        if (this.buyer.equals(compare.buyer)
                && this.amount.equals(compare.amount)
                && this.time.equals(compare.time)){

            return true;
        }

        return false;
    }
}
