package com.epam.entities;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Marat_Chardymau on 3/19/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SubCategory {
    @XmlAttribute
    private String name;
    @XmlElementWrapper(name="products")
    @XmlElement(name="product")
    private List<Product> products;

    public SubCategory() {
    }

    public SubCategory(String name) {
        this.name = name;
    }

    public String getNameasdf() {
        return name;
    }

    public void setNameasdf(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
