package org.sefglobal.githubleaderboad.api.beans;

public class Board {
    private int id;
    private String title;
    private String image;
    private String description;
    private String status;

    public Board() {
    }

    public Board(int id, String title, String image, String description, String status) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
