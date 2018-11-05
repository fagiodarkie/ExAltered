package it.apteroscode.exaltered.core.model.experience;

import java.util.ArrayList;
import java.util.List;

public class GameSessionExperience
{
    private String sessionName;
    public List<ExperienceLogEntry> awardedExperience;

    public GameSessionExperience(String sessionName)
    {
        this.sessionName = sessionName;
        awardedExperience = new ArrayList<>();
    }

    public GameSessionExperience(String sessionName, List<ExperienceLogEntry> entries)
    {
        this.sessionName = sessionName;
        awardedExperience = entries;
    }

    public String getSessionName() {
        return sessionName;
    }
}
