package it.apteroscode.exaltered.core.model.pool;

public class HealthPenalty {
    private double dyingThreshold, bruisedThreshold;

    private int dyingPenalty, bruisedPenalty;

    public HealthPenalty()
    {
        init(0.3, 0.6, 2, 4);
    }

    public HealthPenalty(double bruisedThreshold)
    {
        init (bruisedThreshold, 2 * bruisedThreshold, 2, 4);
    }

    public HealthPenalty(double bruisedThreshold, double dyingThreshold, int bruisedPenalty, int dyingPenalty)
    {
        init (bruisedThreshold, dyingThreshold, bruisedPenalty, dyingPenalty);
    }

    private void init(double bruisedThreshold, double dyingThreshold, int bruisedPenalty, int dyingPenalty)
    {
        if ((bruisedThreshold < 0)
                || (dyingThreshold < 0))
            throw new IllegalArgumentException("provided senseless arguments: thresholds are negative!");

        if ((bruisedThreshold < dyingThreshold)
            || (bruisedPenalty > dyingPenalty))
            throw new IllegalArgumentException("provided senseless arguments: bruised parameters stricter than dying arguments!");

        if ((bruisedThreshold >= 1)
            || (dyingThreshold >= 1))
            throw new IllegalArgumentException("provided senseless arguments: thresholds over 100%!");

        this.bruisedPenalty = bruisedPenalty;
        this.bruisedThreshold = bruisedThreshold;
        this.dyingPenalty = dyingPenalty;
        this.dyingThreshold = dyingThreshold;
    }

    public int getPenalty(Health health)
    {
        if ((health.getMaxHealth() * dyingThreshold) > health.getCurrentHealth())
            return dyingPenalty;
        if ((health.getMaxHealth() * bruisedThreshold) > health.getCurrentHealth())
            return bruisedPenalty;
        return 0;
    }
}
