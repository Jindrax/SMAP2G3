package Experiment;

import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;

import java.io.Serializable;

public class CustomerSkeleton implements Serializable {
    private final long deltaFromPrevious;
    private final Localities locality;
    private final String alias;
    private final Catalog requiredProduct;
    private final int requiredQuantity;

    public CustomerSkeleton(long deltaFromPrevious, Localities locality, String alias, Catalog requiredProduct, int requiredQuantity) {
        this.deltaFromPrevious = deltaFromPrevious;
        this.locality = locality;
        this.alias = alias;
        this.requiredProduct = requiredProduct;
        this.requiredQuantity = requiredQuantity;
    }

    public Localities getLocality() {
        return locality;
    }

    public long getDeltaFromPrevious() {
        return deltaFromPrevious;
    }

    public String getAlias() {
        return alias;
    }

    public Catalog getRequiredProduct() {
        return requiredProduct;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }
}
