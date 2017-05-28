package auction.service;

import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;

/**
 * Created by Stijn on 28-5-2017.
 */
public class ItemsFromSellerTest {
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("veilingPU");
    final EntityManager em = emf.createEntityManager();
    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    public ItemsFromSellerTest() {
    }

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        new DatabaseCleaner(em).clean();
    }

    @Test
    public void numberOfOfferedItems() {

        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        User user1 = registrationMgr.registerUser(email);
        Assert.assertEquals(0, user1.numberOfOfferedItems());

        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerItem(user1, cat, omsch1);


        // test number of items belonging to user1
        //Assert.assertEquals(0, user1.numberOfOfferedItems());
        Assert.assertEquals(1, user1.numberOfOfferedItems());

        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         *  ANSWER:
         *    Because the new Item is offered to a user the offered item list will be increased.
         *    The item is passed to the user object when the item is constructed.
         *    One user can have multiple items.
         */


        Assert.assertEquals(1, item1.getSeller().numberOfOfferedItems());


        User user2 = registrationMgr.getUser(email);
        Assert.assertEquals(1, user2.numberOfOfferedItems());
        Item item2 = sellerMgr.offerItem(user2, cat, omsch2);
        Assert.assertEquals(2, user2.numberOfOfferedItems());

        User user3 = registrationMgr.getUser(email);
        Assert.assertEquals(2, user3.numberOfOfferedItems());

        User userWithItem = item2.getSeller();
        Assert.assertEquals(2, userWithItem.numberOfOfferedItems());
        //Assert.assertEquals(3, userWithItem.numberOfOfferedItems());
        /*
         *  expected: which one of te above two assertions do you expect to be true?
         *  QUESTION:
         *    Explain the result in terms of entity manager and persistance context.
         *  ANSWER:
         *    Because user2 is the seller of item2.
         *    User2 has only 2 items to sell, offerItem method is called twice.
         *    The number of items is 2 and not 3
         */

        /*They are the same*/
        //Assert.assertNotSame(user3, userWithItem);
        Assert.assertEquals(user3, userWithItem);
    }

    @Test
//    @Ignore
    public void getItemsFromSeller() {
        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";
        String omsch2 = "omsch_ifu2";

        Category cat = new Category("cat2");

        User user10 = registrationMgr.registerUser(email);
        Item item10 = sellerMgr.offerItem(user10, cat, omsch1);
        Iterator<Item> it = user10.getOfferedItems();
        // testing number of items of java object
        Assert.assertTrue(it.hasNext());

        // now testing number of items for same user fetched from db.
        User user11 = registrationMgr.getUser(email);
        Iterator<Item> it11 = user11.getOfferedItems();
        Assert.assertTrue(it11.hasNext());
        it11.next();
        Assert.assertFalse(it11.hasNext());

        // Explain difference in above two tests for te iterator of 'same' user
        // Because the user only has one item to sell. When the user is stored in user11 there is a new filled set with items.
        // Therefore the first test passes and the second test not.


        User user20 = registrationMgr.getUser(email);
        Item item20 = sellerMgr.offerItem(user20, cat, omsch2);
        Iterator<Item> it20 = user20.getOfferedItems();
        Assert.assertTrue(it20.hasNext());
        it20.next();
        Assert.assertTrue(it20.hasNext());


        User user30 = item20.getSeller();
        Iterator<Item> it30 = user30.getOfferedItems();
        Assert.assertTrue(it30.hasNext());
        it30.next();
        Assert.assertTrue(it30.hasNext());
    }
}
