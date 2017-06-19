/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAP;

import auction.web.Bid;
import auction.web.Category;
import auction.web.Item;
import auction.web.Money;
import auction.web.User;
import auctionclient.AuctionServiceMethods;
import auctionclient.RegistrationServiceMethods;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stijn
 */
public class AuctionSOAPTest {
    private RegistrationServiceMethods regService;
    private AuctionServiceMethods aucService;
            
    public AuctionSOAPTest() {
        regService = new RegistrationServiceMethods();
        aucService = new AuctionServiceMethods();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getItem() {
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = regService.registerUser(email);
        Category cat = new Category("cat2");
        Item item1 = aucService.offerItem(seller1, cat, omsch);
        Item item2 = aucService.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = regService.registerUser(email3);
        User seller4 = regService.registerUser(email4);
        Category cat = new Category("cat3");
        Item item1 = aucService.offerItem(seller3, cat, omsch);
        Item item2 = aucService.offerItem(seller4, cat, omsch);

        ArrayList<Item> res = (ArrayList<Item>) aucService.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        ArrayList<Item> res2 = (ArrayList<Item>) aucService.findItemByDescription(omsch);
        assertEquals(3, res2.size());

    }

    @Test
    public void newBid() {
        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = regService.registerUser(email);
        User buyer = regService.registerUser(emailb);
        User buyer2 = regService.registerUser(emailb2);
        // eerste bod
        Category cat = new Category("cat9");
        Item item1 = aucService.offerItem(seller, cat, omsch);
        Bid new1 = aucService.newBid(item1, buyer, new Money(10, "eur"));
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Bid new2 = aucService.newBid(item1, buyer2, new Money(9, "eur"));
        assertNotNull(new2);

        // hoger bod
        Bid new3 = aucService.newBid(item1, buyer2, new Money(11, "eur"));
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
    
    @Test
    public void registerUser() {
        User user1 = regService.registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = regService.registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = regService.registerUser("xxx2@yyy2");
        assertEquals(user2bis.getEmail(), user2.getEmail());
        //geen @ in het adres
        assertNull(regService.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = regService.registerUser("xxx5@yyy5");
        User userGet = regService.getUser("xxx5@yyy5");
        assertEquals(userGet.getEmail(), user1.getEmail());
        assertNull(regService.getUser("aaa4@bb5"));
        regService.registerUser("abc");
        assertNull(regService.getUser("abc"));
    }

    @Test
    public void getUsers() {
        List<User> users = regService.getUsers();
        assertEquals(12, users.size());

        User user1 = regService.registerUser("xxx8@yyy");
        users = regService.getUsers();
        assertEquals(13, users.size());
        assertEquals(users.get(12).getEmail(), user1.getEmail());


        User user2 = regService.registerUser("xxx9@yyy");
        users = regService.getUsers();
        assertEquals(14, users.size());

        regService.registerUser("abc");
        //geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = regService.getUsers();
        assertEquals(14, users.size());
    }
    
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = regService.registerUser("xx@nl");
        Category cat = new Category("cat1");
        Item item1 = aucService.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
        aucService.revokeItem(item1);
    }

    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";
        
    
        User seller = regService.registerUser("sel@nl");
        User buyer = regService.registerUser("buy@nl");
        Category cat = new Category("cat1");
        
            // revoke before bidding
        Item item1 = aucService.offerItem(seller, cat, omsch);
        boolean res = aucService.revokeItem(item1);
        assertTrue(res);
        int count = aucService.findItemByDescription(omsch).size();
        assertEquals(0, count);
        
            // revoke after bid has been made
        Item item2 = aucService.offerItem(seller, cat, omsch2);
        aucService.newBid(item2, buyer, new Money(100, "Euro"));
        boolean res2 = aucService.revokeItem(item2);
        assertTrue(res2);
        int count2 = aucService.findItemByDescription(omsch2).size();
        assertEquals(0, count2);  
    }
}
