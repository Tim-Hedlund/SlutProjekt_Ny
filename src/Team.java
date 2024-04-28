import java.util.ArrayList;

public class Team {
    private final int MAX_TEAM_SIZE = 4;
    private ArrayList<Character> teamMembers = new ArrayList<>();
    public Team () {}
    public boolean addCharacter(Character character) { //adds character to team

        if (teamMembers.size() < MAX_TEAM_SIZE) {
            teamMembers.add(character);
            return true;
        } else {
            return false;
        }

    }

    public void setTeam(ArrayList<Character> characters) {

        this.teamMembers = new ArrayList<>();

        teamMembers.addAll(characters);


    }

    public ArrayList<Character> getTeamMembers() {

        return this.teamMembers;

    }
}
