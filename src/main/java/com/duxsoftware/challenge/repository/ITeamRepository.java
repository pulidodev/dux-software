package com.duxsoftware.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duxsoftware.challenge.model.Team;

@Repository
public interface ITeamRepository extends JpaRepository<Team, Long> {

    public Optional<List<Team>> findByNameContainingIgnoreCase(String name);
}
