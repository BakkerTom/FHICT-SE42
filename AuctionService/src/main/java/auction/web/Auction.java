package auction.web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.util.List;
import javax.jws.WebService;
import nl.fontys.util.Money;

/**
 *
 * @author Stijn
 */
@WebService
public class Auction {
    private AuctionMgr auctionMgr = new AuctionMgr();
    private SellerMgr sellerMgr = new SellerMgr();
    
    public Item getItem(Long id) {
        return auctionMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description) {
        return auctionMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount) {
        return auctionMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description) {
        return sellerMgr.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item) {
        return sellerMgr.revokeItem(item);
    }
}
