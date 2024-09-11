package com.duxsoftware.challenge.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duxsoftware.challenge.dto.TeamDTO;
import com.duxsoftware.challenge.exception.TeamNotFoundException;
import com.duxsoftware.challenge.mapper.TeamMapper;
import com.duxsoftware.challenge.model.Team;
import com.duxsoftware.challenge.repository.ITeamRepository;
import com.duxsoftware.challenge.service.ITeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {

    private final ITeamRepository repository;
    private final TeamMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Team not found";

    @Override
    public List<TeamDTO> getAll() {
        List<Team> teams = repository.findAll();
        if (teams.isEmpty()) {
            throw new TeamNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTOList(teams);
    }

    @Override
    public TeamDTO getById(Long id) {
        Optional<Team> team = repository.findById(id);
        if (team.isEmpty()) {
            throw new TeamNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTO(team.get());
    }

    @Override
    public List<TeamDTO> getByName(String name) {
        Optional<List<Team>> teams = repository.findByNameContainingIgnoreCase(name);
        if (teams.get().isEmpty()) {
            throw new TeamNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTOList(teams.get());
    }

    @Override
    public TeamDTO create(TeamDTO teamDTO) {
        Team savedTeam = repository.save(mapper.toEntity(teamDTO));
        return mapper.toDTO(savedTeam);
    }

    @Override
    public TeamDTO update(Long id, TeamDTO teamDTO) {
        Optional<Team> existingTeam = repository.findById(id);
        if (existingTeam.isEmpty()) {
            throw new TeamNotFoundException(NOT_FOUND_MESSAGE);
        }

        mapper.updateEntityFromDTO(teamDTO, existingTeam.get());
        repository.save(existingTeam.get());
        return mapper.toDTO(existingTeam.get());
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new TeamNotFoundException(NOT_FOUND_MESSAGE);
        }
        repository.deleteById(id);
    }
}
