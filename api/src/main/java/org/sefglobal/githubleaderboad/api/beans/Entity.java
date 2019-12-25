package org.sefglobal.githubleaderboad.api.beans;

public class Entity {
    private int id;
    private String name;
    private String image;
    private String status;
    private int boardId;

    public Entity() {
    }

    public Entity(int id, String name, String image, String status, int boardId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
