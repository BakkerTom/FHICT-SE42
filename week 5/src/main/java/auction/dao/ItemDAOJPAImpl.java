package auction.dao;

import auction.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by tom on 22/05/2017.
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private final EntityManager em;

    public ItemDAOJPAImpl(EntityManager manager) {
        this.em = manager;
    }

    @Override
    public int count() {
        Query query = em.createNamedQuery("Item.count", Item.class);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Item item) {
        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();

    }

    @Override
    public Item find(Long id) {
        Item foundItem = null;

        Query q = em.createNamedQuery("Item.findById", Item.class);
        q.setParameter("id", id);

        try {
            foundItem = (Item) q.getSingleResult();
        } catch (NoResultException ex) {

        }

        return foundItem;
    }

    @Override
    public List<Item> findAll() {
        Query q = em.createNamedQuery("Item.findAll", Item.class);
        return q.getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        List<Item> foundItems = null;

        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);

        try {
            foundItems = q.getResultList();
        } catch (NoResultException ex) {

        }

        return foundItems;
    }

    @Override
    public void remove(Item item) {
        em.getTransaction().begin();
        em.remove(em.merge(item));
        em.getTransaction().commit();
    }
}
