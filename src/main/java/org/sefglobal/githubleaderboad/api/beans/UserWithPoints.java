package org.sefglobal.githubleaderboad.api.beans;

public class UserWithPoints extends User {
    private int points;
    private int rank;

    public UserWithPoints() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
