package auction.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Category {

    private String description;

    public Category() {
        description = "undefined";
    }

    public Category(String description) {
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }
}
