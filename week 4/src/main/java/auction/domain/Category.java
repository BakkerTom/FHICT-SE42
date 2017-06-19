package auction.domain;

import javax.persistence.*;

@Embeddable
public class Category {

    private String description;

    /**
     * Create a new Category object.
     * Value of description will be undefined.
     */
    public Category() {
        description = "undefined";
    }

    /**
     * Create a new Category object.
     * @param description Text that describes this category
     */
    public Category(String description) {
        this.description = description;
    }

    /**
     * Get the description from this category.
     * @return String description.
     */
    public String getDiscription() {
        return description;
    }
}
