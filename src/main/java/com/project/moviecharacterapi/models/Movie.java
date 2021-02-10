package com.project.moviecharacterapi.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "genre")
    private String genre;

    @Column(name = "release_year")
    private String releaseYear;

    @Column(name = "director_name")
    private String director;

    @Column(name = "picture_url")
    private String picture;

    @Column(name = "trailer_url")
    private String trailer;

    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    private List<Character> characters;

    @JsonGetter("characters")
    public List<String> getJsonCharacters() {
        if(characters != null) {
            return characters.stream()
                    .map(character -> {
                        return "/api/v1/characters/" + character.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @JsonGetter("franchise")
    public String getJsonFranchises() {
        if (franchise != null) {
            return "/api/v1/franchises/" + franchise.getId();
        } else {
            return null;
        }
    }

    public Movie() {
    }

    public Movie(Long id, String movieTitle, String genre, String releaseYear, String director, String picture, String trailer) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.picture = picture;
        this.trailer = trailer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }
}
