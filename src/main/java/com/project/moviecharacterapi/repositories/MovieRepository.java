package com.project.moviecharacterapi.repositories;

import com.project.moviecharacterapi.models.Franchise;
import com.project.moviecharacterapi.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByFranchise(Franchise franchise);
}
