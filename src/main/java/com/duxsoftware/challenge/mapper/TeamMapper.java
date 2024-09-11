package com.duxsoftware.challenge.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.duxsoftware.challenge.dto.TeamDTO;
import com.duxsoftware.challenge.model.Team;

@Component
public class TeamMapper {

    public TeamDTO toDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .league(team.getLeague())
                .country(team.getCountry())
                .build();
    }

    public Team toEntity(TeamDTO teamDTO) {
        return Team.builder()
                .name(teamDTO.getName())
                .league(teamDTO.getLeague())
                .country(teamDTO.getCountry())
                .build();
    }

    public List<TeamDTO> toDTOList(List<Team> teams) {
        return teams.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(TeamDTO teamDTO, Team team) {
        if (teamDTO.getName() != null) {
            team.setName(teamDTO.getName());
        }
        if (teamDTO.getLeague() != null) {
            team.setLeague(teamDTO.getLeague());
        }
        if (teamDTO.getCountry() != null) {
            team.setCountry(teamDTO.getCountry());
        }
    }
}