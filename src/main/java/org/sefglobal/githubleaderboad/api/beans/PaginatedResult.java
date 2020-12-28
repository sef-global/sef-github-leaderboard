package org.sefglobal.githubleaderboad.api.beans;

public class PaginatedResult {
    private int count;
    private Object data;

    public PaginatedResult() {
    }

    public PaginatedResult(int count, Object data) {
        this.count = count;
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
