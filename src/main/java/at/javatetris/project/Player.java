package at.javatetris.project;

import java.util.List;

/**
 * Player class
 * @author Severin Rosner
 */
public class Player {
    /**
     * username
     */
    private String name;

    /**
     * highscore classic
     */
    private int hsClassic;

    /**
     * highscore time
     */
    private int hsTime;

    /**
     * highscore infinity
     */
    private int hsInfinity;

    /**
     * games played
     */
    private int gamesPlayed;

    /**
     * time played
     */
    private int timePlayed;

    /**
     * get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the classic highscore
     * @return hs_classic
     */
    public int getHsClassic() {
        return hsClassic;
    }

    /**
     * get the time highscore
     * @return hs_time
     */
    public int getHsTime() {
        return hsTime;
    }

    /**
     * get the infinity highscore
     * @return hs_infinity
     */
    public int getHsInfinity() {
        return hsInfinity;
    }

    /**
     * get the played games
     * @return gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * get the played time
     * @return timePlayed
     */
    public int getTimePlayed() {
        return timePlayed;
    }

    /**
     * constructor for player
     * @param name username
     * @param hsClassic classic highscore
     * @param hsTime time highscore
     * @param hsInfinity infinity highscore
     * @param gamesPlayed played games
     * @param timePlayed time played
     */
    public Player(String name, int hsClassic, int hsTime, int hsInfinity, int timePlayed, int gamesPlayed) {
        this.name = name;
        this.hsClassic = hsClassic;
        this.hsTime = hsTime;
        this.hsInfinity = hsInfinity;
        this.timePlayed = timePlayed;
        this.gamesPlayed = gamesPlayed;
    }


    @Override
    public String toString() {
        return "[name: " + name + ", hs_classic: " + hsClassic + ", hs_time: " + hsTime + ", hs_infinity: " + hsInfinity + ", gamesPlayed: " + gamesPlayed + ", timePlayed: " + timePlayed + "]";
    }

    /**
     * find the user with the name in the list
     * @param name the name (username) to search for
     * @param playersList list of players
     * @return the Player with the name
     */
    public static Player findUser(String name, List<Player> playersList) {
        for (Player player : playersList) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }
}
