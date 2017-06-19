package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private FontysTime time;
    private User buyer;
    private Money amount;

    public Bid(){};

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
}
