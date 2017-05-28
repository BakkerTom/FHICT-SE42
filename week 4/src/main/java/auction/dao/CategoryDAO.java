package auction.dao;

import auction.domain.Category;
import auction.domain.Item;

import java.util.List;

/**
 * Created by Stijn on 28-5-2017.
 */
public interface CategoryDAO {

    /**
     *
     * @return number of category instances
     */
    int count();

    /**
     * The category is persisted.
     * @param cat
     */
    void create(Category cat);

    /**
     * Merge the state of the given category into persistant context.
     * @param cat
     */
    void edit(Category cat);

    /**
     * Remove the entity instance
     * @param cat - entity instance
     */
    void remove(Category cat);
}
