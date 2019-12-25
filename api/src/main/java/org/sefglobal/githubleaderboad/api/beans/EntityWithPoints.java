package org.sefglobal.githubleaderboad.api.beans;

public class EntityWithPoints extends Entity {
    int points;
    int rank;

    public EntityWithPoints() {
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
