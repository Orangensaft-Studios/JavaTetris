package at.javatetris.project;

public class Player {
    private String name;

    private int hs_classic;

    private int hs_time;

    private int hs_infinity;

    private int gamesPlayed;

    private int timePlayed;

    public String getName() {
        return name;
    }

    public int getHs_classic() {
        return hs_classic;
    }

    public int getHs_time() {
        return hs_time;
    }

    public int getHs_infinity() {
        return hs_infinity;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public Player(String name, int hs_classic, int hs_time, int hs_infinity, int gamesPlayed, int timePlayed) {
        this.name = name;
        this.hs_classic = hs_classic;
        this.hs_time = hs_time;
        this.hs_infinity = hs_infinity;
        this.gamesPlayed = gamesPlayed;
        this.timePlayed = timePlayed;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", hs_classic: " + hs_classic + ", hs_time: " + hs_time + ", hs_infinity: " + hs_infinity + ", gamesPlayed: " + gamesPlayed + ", timePlayed: " + timePlayed + "]";
    }
}
