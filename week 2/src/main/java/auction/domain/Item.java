package auction.domain;

import nl.fontys.util.Money;

public class Item implements Comparable {

    private Long id;
    private User seller;
    private Category category;
    private String description;
    private Bid highest;

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
