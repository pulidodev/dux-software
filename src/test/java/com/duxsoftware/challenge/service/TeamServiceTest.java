package com.duxsoftware.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.duxsoftware.challenge.dto.TeamDTO;
import com.duxsoftware.challenge.exception.TeamNotFoundException;
import com.duxsoftware.challenge.mapper.TeamMapper;
import com.duxsoftware.challenge.model.Team;
import com.duxsoftware.challenge.repository.ITeamRepository;
import com.duxsoftware.challenge.service.impl.TeamServiceImpl;

class TeamServiceTest {

    @Mock
    private ITeamRepository repository;

    @Mock
    private TeamMapper mapper;

    @InjectMocks
    private TeamServiceImpl service;

    private Team team;

    private TeamDTO teamDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        team = Team.builder()
                .id(1L)
                .name("Manchester United")
                .league("Premier League")
                .country("Inglaterra")
                .build();
        teamDTO = TeamDTO.builder()
                .id(1L)
                .name("Manchester United")
                .league("Premier League")
                .country("Inglaterra")
                .build();
    }

    @Test
    void testGetAllTeamsExist() {
        List<Team> teams = List.of(team);
        when(repository.findAll()).thenReturn(teams);
        when(mapper.toDTOList(teams)).thenReturn(List.of(teamDTO));

        List<TeamDTO> result = service.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetAllNoTeamsFound() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(TeamNotFoundException.class, () -> service.getAll());

        verify(repository).findAll();
    }

    @Test
    void testGetByIdTeamExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(team));
        when(mapper.toDTO(team)).thenReturn(teamDTO);

        TeamDTO result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Manchester United", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    void testGetByIdTeamNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> service.getById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void testGetByNameTeamsExist() {
        List<Team> teams = List.of(team);
        when(repository.findByNameContainingIgnoreCase("Team")).thenReturn(Optional.of(teams));
        when(mapper.toDTOList(teams)).thenReturn(List.of(teamDTO));

        List<TeamDTO> result = service.getByName("Team");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByNameContainingIgnoreCase("Team");
    }

    @Test
    void testGetByNameNoTeamsFound() {
        when(repository.findByNameContainingIgnoreCase("Team")).thenReturn(Optional.of(new ArrayList<>()));

        assertThrows(TeamNotFoundException.class, () -> service.getByName("Team"));

        verify(repository).findByNameContainingIgnoreCase("Team");
    }

    @Test
    void testCreateTeamCreated() {
        when(repository.save(any(Team.class))).thenReturn(team);
        when(mapper.toEntity(any(TeamDTO.class))).thenReturn(team);
        when(mapper.toDTO(team)).thenReturn(teamDTO);

        TeamDTO result = service.create(teamDTO);

        assertNotNull(result);
        assertEquals("Manchester United", result.getName());
        verify(repository).save(any(Team.class));
    }

    @Test
    void testUpdateTeamExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(team));
        doNothing().when(mapper).updateEntityFromDTO(teamDTO, team);
        when(repository.save(team)).thenReturn(team);
        when(mapper.toDTO(team)).thenReturn(teamDTO);

        TeamDTO result = service.update(1L, teamDTO);

        assertNotNull(result);
        assertEquals("Manchester United", result.getName());
        verify(repository).findById(1L);
        verify(repository).save(team);
    }

    @Test
    void testUpdateTeamNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> service.update(1L, teamDTO));

        verify(repository).findById(1L);
    }

    @Test
    void testDeleteByIdTeamExists() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteByIdTeamNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(TeamNotFoundException.class, () -> service.deleteById(1L));

        verify(repository).existsById(1L);
    }
}
