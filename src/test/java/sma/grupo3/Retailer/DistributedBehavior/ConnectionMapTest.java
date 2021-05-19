package sma.grupo3.Retailer.DistributedBehavior;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sma.grupo3.Retailer.Utils.Configuration;

import java.util.ArrayList;
import java.util.List;

class ConnectionMapTest {

    @Test
    void shortestPath() {
        List<Localities> path = ConnectionMap.shortestPath(Localities.CHAPINERO, Localities.USAQUEN);
        List<Localities> expectedPath = new ArrayList<Localities>() {{
            add(Localities.USAQUEN);
        }};
        MatcherAssert.assertThat(path, Is.is(expectedPath));
    }

    @Test
    void getTimeFromTo(){
        double time = ConnectionMap.getTimeFromTo(Localities.CHAPINERO, Localities.USAQUEN);
        double expectedTime = 1735000 * Configuration.getDouble("TIME_SCALE_FACTOR");
        Assertions.assertEquals(time, expectedTime);
    }
}