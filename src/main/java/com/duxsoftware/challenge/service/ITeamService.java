package com.duxsoftware.challenge.service;

import java.util.List;

import com.duxsoftware.challenge.dto.TeamDTO;

public interface ITeamService {

    public List<TeamDTO> getAll();

    public TeamDTO getById(Long id);

    public List<TeamDTO> getByName(String name);

    public TeamDTO create(TeamDTO teamDTO);

    public TeamDTO update(Long id, TeamDTO teamDTO);

    public void deleteById(Long id);
}
