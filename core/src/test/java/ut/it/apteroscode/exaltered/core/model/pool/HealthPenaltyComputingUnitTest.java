package ut.it.apteroscode.exaltered.core.model.pool;

import it.apteroscode.exaltered.core.model.pool.Health;
import it.apteroscode.exaltered.core.model.pool.HealthPenalty;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealthPenaltyComputingUnitTest {

    private static int lowPenalty = 2, highPenalty = 4;
    private HealthPenalty sut = new HealthPenalty(0.5, 0.25, lowPenalty, highPenalty);

    @Test
    public void shouldComputeHealthPenaltyCorrectlyWithFullHealth()
    {
        Health health = mockHealth(100);

        Assert.assertEquals(sut.getPenalty(health), 0);
    }

    @Test
    public void shouldComputeHealthPenaltyCorrectlyWhenDying()
    {
        Health health = mockHealth(20);

        Assert.assertEquals(sut.getPenalty(health), highPenalty);
    }

    @Test
    public void shouldComputeHealthPenaltyCorrectlyWhenBruised()
    {
        Health health = mockHealth(40);

        Assert.assertEquals(sut.getPenalty(health), lowPenalty);
    }

    private Health mockHealth(double percentage )
    {
        Health result = mock(Health.class);

        when(result.getMaxHealth()).thenReturn(100D);
        when(result.getCurrentHealth()).thenReturn(percentage);

        return result;
    }
}
