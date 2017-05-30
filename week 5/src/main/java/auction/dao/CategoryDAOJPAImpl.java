package auction.dao;

import auction.domain.Category;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by Stijn on 28-5-2017.
 */
public class CategoryDAOJPAImpl implements CategoryDAO {

    private final EntityManager em;

    public CategoryDAOJPAImpl() {
        this.em = Persistence.createEntityManagerFactory("veilingPU").createEntityManager();
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void create(Category cat) {
        em.getTransaction().begin();
        em.persist(cat);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Category cat) {

    }

    @Override
    public void remove(Category cat) {

    }
}
