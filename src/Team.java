import java.util.ArrayList;

//team is the team that the player controls, it is limited to 4 as maximum.
public class Team {
    private final int MAX_TEAM_SIZE = 4;
    private ArrayList<Character> teamMembers = new ArrayList<>();
    public Team () {}

    //adds a single character to the team
    public boolean addCharacter(Character character) {

        if (teamMembers.size() < MAX_TEAM_SIZE) {
            teamMembers.add(character);
            return true;
        } else {
            return false;
        }

    }

    //sets the team to a new set of characters
    public void setTeam(ArrayList<Character> characters) {

        this.teamMembers = new ArrayList<>();

        teamMembers.addAll(characters);

    }

    public ArrayList<Character> getTeamMembers() {

        return this.teamMembers;

    }
}
