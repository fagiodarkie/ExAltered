package ut.it.apteroscode.exaltered.core.model.pool;

import it.apteroscode.exaltered.core.model.pool.HealthPenalty;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(Parameterized.class)
public class HealthPenaltyEdgeCasesUnitTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {2D, 1D, 1, 2},
            {0.5, 0.2, 4, 2},
            {0.2, 0.5, 1, 2},
            {-0.5, 0.2, 1, 2},
            {0.5, -0.2, 1, 2}
        });
    }

    private double bruiseT, dyingT;
    private int bruiseP, dyingP;

    public HealthPenaltyEdgeCasesUnitTest(Double bruiseT, Double dyingT, Integer bruiseP, Integer dyingP)
    {
        this.bruiseT = bruiseT;
        this.dyingT = dyingT;
        this.bruiseP = bruiseP;
        this.dyingP = dyingP;
    }

    @Test
    public void shouldBreakWhenInstantiatedWithWrongParameter()
    {
        try
        {
            HealthPenalty sut = new HealthPenalty(bruiseT, dyingT, bruiseP, dyingP);
        }
        catch(Exception e)
        {
            Assert.assertThat(e, instanceOf(IllegalArgumentException.class));
            return;
        }
        Assert.fail(String.format("Exception expected, none found for parameters: %f, %f, %d, %d.",
                bruiseT, dyingT, bruiseP, dyingP));
    }
}
