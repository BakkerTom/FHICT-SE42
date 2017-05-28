package auction.dao;

import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * Created by Stijn on 10-5-2017.
 */
public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOJPAImpl(EntityManager manager) {
        this.em = manager;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    @Override
    public List<User> findAll() {
        Query q = em.createNamedQuery("User.getAll", User.class);
        return (List<User>) q.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        User foundUser = null;
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);

        try{
            foundUser = (User) q.getSingleResult();
        }
        catch (NoResultException ex) {

        }

        return foundUser;
    }

    @Override
    public void remove(User user) {
        em.getTransaction().begin();
        em.remove(em.merge(user));
        em.getTransaction().commit();
    }

    @Override
    public Set<Item> findOfferedItems() {
        Query q = em.createNamedQuery("User.getOfferedItems", User.class);
        return (Set<Item>) q.getResultList();
    }
}
