package sma.grupo3.Retailer.SharedDomain;

import java.io.Serializable;

public enum Catalog implements Serializable {
    Product1("Product1", 600),
    Product2("Product2", 2000),
    Product3("Product3", 150),
    Product4("Product4", 390),
    Product5("Product5", 1300);

    public final String name;
    public final double weight;

    Catalog(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }


}
