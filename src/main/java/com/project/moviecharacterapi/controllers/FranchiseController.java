package com.project.moviecharacterapi.controllers;

import com.project.moviecharacterapi.models.Character;
import com.project.moviecharacterapi.models.Franchise;
import com.project.moviecharacterapi.models.Movie;
import com.project.moviecharacterapi.repositories.CharacterRepository;
import com.project.moviecharacterapi.repositories.FranchiseRepository;
import com.project.moviecharacterapi.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    private final FranchiseRepository franchiseRepository;
    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;

    public FranchiseController(FranchiseRepository franchiseRepository, MovieRepository movieRepository, CharacterRepository characterRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        HttpStatus status;
        List<Franchise> franchise = franchiseRepository.findAll();
        if(franchise.size() ==0){
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(franchise, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getSpecificFranchises(@PathVariable Long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Franchise returnFranchise = new Franchise();
        boolean exists = franchiseRepository.existsById(id);
        if(exists){
            returnFranchise = franchiseRepository.findById(id).get();
            status = HttpStatus.OK;
            return new ResponseEntity<>(returnFranchise, status);
        }
        return new ResponseEntity<>(null, status);
    }

    //Get all the movies for a franchise
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Movie>> getMoviesForFranchise(@PathVariable Long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Franchise returnFranchise = new Franchise();
        boolean exists = franchiseRepository.existsById(id);
        List<Movie> moviesForFranchise = new ArrayList<>();
        if(exists){
            returnFranchise = franchiseRepository.findById(id).get();
            status = HttpStatus.OK;
            moviesForFranchise = movieRepository.findAllByFranchise(returnFranchise);
        }
        return new ResponseEntity<>(moviesForFranchise, status);
    }

    //Get all the characters for a franchise
    @GetMapping("/{id}/characters")
    public ResponseEntity<List<List<Character>>> getCharactersForFranchise(@PathVariable Long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Franchise returnFranchise = new Franchise();
        boolean exists = franchiseRepository.existsById(id);
        List<Movie> moviesForFranchise = new ArrayList<>();
        List<List<Character>> charactersForFranchise = new ArrayList<>();

        if(exists){
            returnFranchise = franchiseRepository.findById(id).get();
            status = HttpStatus.OK;
            moviesForFranchise = movieRepository.findAllByFranchise(returnFranchise);
            for(Movie movie : moviesForFranchise){
                charactersForFranchise.add(characterRepository.findAllByMovies(movie));
            }
        }
        return new ResponseEntity<>(charactersForFranchise, status);
    }

    @PostMapping
    public ResponseEntity<Franchise> addNewFranchise(@RequestBody Franchise franchise) {
        HttpStatus status = HttpStatus.CREATED;
        Franchise returnFranchise = franchiseRepository.save(franchise);
        return new ResponseEntity<>(returnFranchise, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise){
        Franchise returnFranchise = new Franchise();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if(!id.equals(franchise.getId())){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(null, status);
        }
        returnFranchise = franchiseRepository.save(franchise);
        return new ResponseEntity<>(returnFranchise, status);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFranchise(@RequestBody Franchise franchise){
        HttpStatus status = HttpStatus.NO_CONTENT;
        if(franchiseRepository.existsById(franchise.getId())){
            franchiseRepository.delete(franchise);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }
}
