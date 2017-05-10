package auction.dao;

import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        em.merge(user);
    }

    @Override
    public List<User> findAll() {
        Query q = em.createNamedQuery("User.getAll", User.class);
        return (List<User>) q.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        return (User) q.getSingleResult();
    }

    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }
}
