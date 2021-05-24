package sma.grupo3.Retailer.SharedDomain;

import sma.grupo3.Retailer.Utils.Configuration;

public enum StandardMalfunction {
    BRAKE(3744000),
    TIRE(2808000),
    ENGINE(2592000);

    public long time;

    StandardMalfunction(long time) {
        this.time = (long) (time * Configuration.getDouble("MALFUNCTION_TIME_SCALE_FACTOR"));
    }
}
