package com.driver;

import com.driver.models.Genre;

public class BookRequestDto {

    String name;
    Genre genre;

    boolean available;

    int authorId ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public BookRequestDto() {
    }

    public BookRequestDto(String name, Genre genre, boolean available, int authorId) {
        this.name = name;
        this.genre = genre;
        this.available = available;
        this.authorId = authorId;
    }
}
