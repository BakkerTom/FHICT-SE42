import bank.domain.Account;
import bank.service.AccountMgr;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.Assert;
import org.junit.Before;
import util.DatabaseCleaner;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
    public void ClearDatabase() throws Exception {
        DatabaseCleaner cleaner = new DatabaseCleaner(em);
        cleaner.clean();
    }

    @Test
    public void assignmentOne() throws Exception {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        Assert.assertEquals(1L, (long) account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        //TODO: verklaar en pas eventueel aan
        Assert.assertTrue(account.getId() > 0L);

    }
}
