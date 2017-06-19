/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

import auction.web.AuctionService;
import auction.web.Bid;
import auction.web.Category;
import auction.web.Item;
import auction.web.Money;
import auction.web.User;
import java.util.List;

/**
 *
 * @author Stijn
 */
public class AuctionServiceMethods {
    private static final AuctionService service = new AuctionService();
    
    public static Item getItem(Long id) {
        auction.web.Auction port = service.getAuctionPort();
        return port.getItem(id);
    }
    
    public static List<Item> findItemByDescription(String description) {
        auction.web.Auction port = service.getAuctionPort();
        return port.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount) {
        auction.web.Auction port = service.getAuctionPort();
        return port.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description) {
        auction.web.Auction port = service.getAuctionPort();
        return port.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item) {
        auction.web.Auction port = service.getAuctionPort();
        return port.revokeItem(item);
    }
}
