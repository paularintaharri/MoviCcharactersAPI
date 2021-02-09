package com.project.moviecharacterapi.controllers;

import com.project.moviecharacterapi.repositories.CharacterRepository;
import com.project.moviecharacterapi.repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FranchiseController {
    final
    FranchiseRepository franchiseRepository;

    public FranchiseController(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }
}
