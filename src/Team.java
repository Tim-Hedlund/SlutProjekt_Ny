import java.util.ArrayList;

public class Team {
    final int MAX_TEAM_SIZE = 4;
    ArrayList<Character> teamMembers = new ArrayList<>();
    Team () {}
    public boolean addCharacter(Character character) { //adds character to team

        if (teamMembers.size() < MAX_TEAM_SIZE) {
            teamMembers.add(character);
            return true;
        } else {
            return false;
        }

    }

}
