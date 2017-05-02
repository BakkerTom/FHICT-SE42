import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import bank.service.AccountMgr;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.Assert;
import org.junit.Before;
import util.DatabaseCleaner;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by tom on 02/05/2017.
 */

public class Tests {

    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
    }

    @org.junit.Test
    public void clearDatabase() throws Exception {
        DatabaseCleaner cleaner = new DatabaseCleaner(em);
        cleaner.clean();
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
}
