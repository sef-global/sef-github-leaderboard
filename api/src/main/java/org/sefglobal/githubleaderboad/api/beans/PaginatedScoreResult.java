package org.sefglobal.githubleaderboad.api.beans;

public class PaginatedScoreResult extends PaginatedResult {
    private int points;

    public PaginatedScoreResult(int count, int points, Object data) {
        super(count, data);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
