package com.project.moviecharacterapi.controllers;

import com.project.moviecharacterapi.models.Character;
import com.project.moviecharacterapi.repositories.CharacterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {
    private final CharacterRepository characterRepository;

    public CharacterController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Character>> getAllCharacters() {
        HttpStatus status;
        List<Character> characters = characterRepository.findAll();
        if(characters.size() ==0){
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(characters, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getSpecificCharacters(@PathVariable Long id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Character returnCharacter = new Character();
        boolean exists = characterRepository.existsById(id);
        if(exists){
            returnCharacter = characterRepository.findById(id).get();
            status = HttpStatus.OK;
            return new ResponseEntity<>(returnCharacter, status);
        }
        return new ResponseEntity<>(null, status);
    }

    @PostMapping
    public ResponseEntity<Character> addNewCharacter(@RequestBody Character character) {
        HttpStatus status = HttpStatus.CREATED;
        Character returnCharacter = characterRepository.save(character);
        return new ResponseEntity<>(returnCharacter, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody Character character){
        Character returnCharacter = new Character();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if(!id.equals(character.getId())){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(null, status);
        }
        returnCharacter = characterRepository.save(character);
        return new ResponseEntity<>(returnCharacter, status);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCharacter(@RequestBody Character character){
        HttpStatus status = HttpStatus.NO_CONTENT;
        if(characterRepository.existsById(character.getId())){
            characterRepository.delete(character);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }
}
