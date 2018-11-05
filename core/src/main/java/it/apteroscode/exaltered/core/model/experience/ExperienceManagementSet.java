package it.apteroscode.exaltered.core.model.experience;

import java.util.List;
import java.util.stream.Collectors;

public class ExperienceManagementSet {

    private List<GameSessionExperience> playedSessions;
    private List<ExperienceLogEntry> expenses;

    public ExperienceManagementSet(List<GameSessionExperience> sessions,
                                   List<ExperienceLogEntry> expenses)
    {
        this.playedSessions = sessions;
        this.expenses = expenses;
    }

    public void addGameSession(GameSessionExperience experience)
    {
        playedSessions.add(experience);
    }

    public void purchaseWithExperience(ExperienceLogEntry purchasedThing)
    {
        expenses.add(purchasedThing);
    }

    public List<ExperienceLogEntry> getExpenses()
    {
        return expenses;
    }

    public List<GameSessionExperience> getPlayedSessions()
    {
        return playedSessions;
    }

    public Double getBankedExperience()
    {
        return getTotalExperience() - getSpentExperience();
    }

    public Double getSpentExperience()
    {
        return expenses.stream().mapToDouble(expense -> expense.getAmount()).sum();
    }

    public Double getTotalExperience()
    {
        return playedSessions.stream()
                .mapToDouble(session ->
                    session.awardedExperience.stream()
                        .mapToDouble(entry -> entry.getAmount())
                        .sum())
                .sum();
    }
}
