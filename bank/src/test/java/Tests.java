import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import bank.service.AccountMgr;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import util.DatabaseCleaner;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.*;

/**
 * Created by tom on 02/05/2017.
 */

public class Tests {

    private EntityManager em;
    private EntityManagerFactory factory;
    private DatabaseCleaner dbCleaner;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("bankPU");
        em = factory.createEntityManager();
        dbCleaner = new DatabaseCleaner(em);
    }
    @After
    public void clearDatabase() throws Exception {
        dbCleaner.clean();
    }

    @Test
    public void assignmentOne() throws Exception {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //Autoincrement waardoor het ID gelijk op 1 werdt gezet ipv Null
        Assert.assertEquals(1L, (long) account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //Vanwege de commit is de entity opgeslagen en het id groter dan 0
        Assert.assertTrue(account.getId() > 0L);
    }

    @Test
    public void assignmentTwo() throws Exception {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().rollback();

        // We vragen dmv de AccountDAOJPAImpl alle accounts op. Wanneer de size van deze list gelijk is aan 0 is de rollback geslaagd.
        List<Account> accounts = new AccountDAOJPAImpl(em).findAll();
        Assert.assertEquals(0, accounts.size());

    }

    @Test
    public void assignmentThree() throws Exception {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.persist(account);
        em.getTransaction().begin();
        //TODO: verklaar en pas eventueel aan
        Assert.assertEquals(expected, account.getId());
        em.flush();
        //TODO: verklaar en pas eventueel aan
        Assert.assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
        //TODO: verklaar en pas eventueel aan
    }

    @Test
    public void assignmentFour() throws Exception {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.persist(account);
        em.getTransaction().begin();
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        Assert.assertEquals(expectedBalance, account.getBalance());
        //De balans wordt aanpast en de transactie wordt vervolgens gecommit. De gegevens worden dan opgeslagen en daarom is de assert vergelijking waar.
        Long  cid = account.getId();

        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        //Er wordt een tweede entity manager aangemaakt deze manager zoekt naar een account op basis van ID. Deze wordt gevonden en daarom is de assert vergelijking waar.
        Assert.assertEquals(expectedBalance, found.getBalance());
    }

    @Test
    public void assignmentFive() throws Exception {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        Assert.assertEquals(expectedBalance, account.getBalance());
        //De balans wordt aanpast en de transactie wordt vervolgens gecommit. De gegevens worden dan opgeslagen en daarom is de assert vergelijking waar.
        Long  cid = account.getId();

        EntityManager em2 = factory.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class,  cid);
        //Er wordt een tweede entity manager aangemaakt deze manager zoekt naar een account op basis van ID. Deze wordt gevonden en daarom is de assert vergelijking waar.
        Assert.assertEquals(expectedBalance, found.getBalance());

        Long newExpectedBalance = 100L;
        found.setAccountNr(newExpectedBalance);
        em2.getTransaction().commit();
        em.refresh(account);
        Assert.assertEquals(newExpectedBalance, account.getAccountNr());
    }

    @Test
    public void assignmentSix() throws Exception {
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        Assert.assertEquals(balance1, acc.getBalance());


        // scenario 2
        Long balance2a = 211L;
        Long balance2b = 422L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a+balance2a);
        em.getTransaction().commit();
        Assert.assertEquals(balance2a, acc.getBalance());
        Assert.assertEquals(balance2b, acc9.getBalance());
        Assert.assertEquals(acc9, new AccountDAOJPAImpl(em).findByAccountNr(2L));
        Assert.assertEquals(acc.getAccountNr(), new AccountDAOJPAImpl(em).findByAccountNr(2L).getAccountNr());


        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        Assert.assertFalse(em.contains(acc)); //False omdat em niet meer geintereseerd is in acc.
        Assert.assertTrue(em.contains(acc2)); //True omdat em wel geintereseerd is in acc2.
        Assert.assertNotEquals(acc,acc2);  //Objecten zijn niet hetzelfde
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit() ;
        Assert.assertEquals(balance3b, acc2.getBalance());
        Assert.assertEquals(balance3c, acc.getBalance());


        // scenario 4
        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        EntityManager em = factory.createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;

        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;
        Assert.assertEquals((Long)650L,account2.getBalance()) ;  //Zodra de setBalance methode wordt aangeroepen wordt de value van account 2 ook overschreven een reference. Hier is de assert vergelijking true.
        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;
        Assert.assertSame(account,account2) ;  //Omdat ze hetzelfde ID hebben is merge een sync gevolg en klopt de vergelijking
        Assert.assertTrue(em.contains(account2)) ;  //Account 2 is ook een acount en bestaat daar door.
        Assert.assertFalse(em.contains(tweedeAccountObject)) ;  //TweedeAccountObject is nooit gecommit dus niet bekend bij em.
        tweedeAccountObject.setBalance(850l) ;
        Assert.assertEquals((Long)650L,account.getBalance()) ;
        Assert.assertEquals((Long)650L,account2.getBalance()) ;  //Vanwege de merge zijn account en account 2 gelijk daardoor klopt de vergelijking
        em.getTransaction().commit() ;
        em.close() ;
    }

    @Test
    public void assignmentSeven() throws Exception {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        // scenario 1
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        Assert.assertSame(accF1, accF2);

        // scenario 2
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        Assert.assertNotSame(accF1, accF2);
        //Bij senario 1 worden twee account objecten gevuld met em.find het id van acc1, daarom zijn dus precies hetzelfde
        //Bij senario 2 wordt clear gebruikt en daarom is accF1 niet meer bekend. Daarom dus ook niet gelijk aan accF2.
    }

    @Test
    public void assignmentEight() throws Exception {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        Assert.assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        Assert.assertNull(accFound);
        //ACC1 is nog steeds lokaal bekend en daarom is de vergelijking van ID succesvol. De em.find mislukt omdat acc1 eerder verwijderd is met em.remove
        //De assertNull is daarom ook succesvol omdat accFound null is.
    }

    @Test
    public void assignmentNine() throws Exception {
        /*
        * GenerationType table
        * Deze techniek gaat ervan uit dat de persistence provider verantwoordelijk is voor het verzorgen van primary keys door een database tabel te gebruiken
        * GenerationType sequence
        * Deze techniek gaat ervan uit dat de persistence provider verantwoordelijk is voor het verzorgen van primary keys door een database sequence*/
        //Zowel bij table als bij sequence werken alle tests nog steeds.
    }
}
