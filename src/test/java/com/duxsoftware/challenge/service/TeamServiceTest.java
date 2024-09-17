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

import com.duxsoftware.challenge.dto.EquipoDTO;
import com.duxsoftware.challenge.exception.EquipoNotFoundException;
import com.duxsoftware.challenge.mapper.EquipoMapper;
import com.duxsoftware.challenge.model.Equipo;
import com.duxsoftware.challenge.repository.IEquipoRepository;
import com.duxsoftware.challenge.service.impl.EquipoServiceImpl;

class EquipoServiceTest {

    @Mock
    private IEquipoRepository repository;

    @Mock
    private EquipoMapper mapper;

    @InjectMocks
    private EquipoServiceImpl service;

    private Equipo equipo;

    private EquipoDTO equipoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equipo = Equipo.builder()
                .id(1L)
                .nombre("Manchester United")
                .liga("Premier League")
                .pais("Inglaterra")
                .build();
        equipoDTO = EquipoDTO.builder()
                .id(1L)
                .nombre("Manchester United")
                .liga("Premier League")
                .pais("Inglaterra")
                .build();
    }

    @Test
    void testGetAllEquiposExist() {
        List<Equipo> equipos = List.of(equipo);
        when(repository.findAll()).thenReturn(equipos);
        when(mapper.toDTOList(equipos)).thenReturn(List.of(equipoDTO));

        List<EquipoDTO> result = service.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testGetAllNoEquiposFound() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(EquipoNotFoundException.class, () -> service.getAll());

        verify(repository).findAll();
    }

    @Test
    void testGetByIdEquipoExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(equipo));
        when(mapper.toDTO(equipo)).thenReturn(equipoDTO);

        EquipoDTO result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Manchester United", result.getNombre());
        verify(repository).findById(1L);
    }

    @Test
    void testGetByIdEquipoNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EquipoNotFoundException.class, () -> service.getById(1L));

        verify(repository).findById(1L);
    }

    @Test
    void testGetByNombreEquiposExist() {
        List<Equipo> equipos = List.of(equipo);
        when(repository.findByNombreContainingIgnoreCase("Equipo")).thenReturn(Optional.of(equipos));
        when(mapper.toDTOList(equipos)).thenReturn(List.of(equipoDTO));

        List<EquipoDTO> result = service.getByNombre("Equipo");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByNombreContainingIgnoreCase("Equipo");
    }

    @Test
    void testGetByNombreNoEquiposFound() {
        when(repository.findByNombreContainingIgnoreCase("Equipo")).thenReturn(Optional.of(new ArrayList<>()));

        assertThrows(EquipoNotFoundException.class, () -> service.getByNombre("Equipo"));

        verify(repository).findByNombreContainingIgnoreCase("Equipo");
    }

    @Test
    void testCreateEquipoCreated() {
        when(repository.save(any(Equipo.class))).thenReturn(equipo);
        when(mapper.toEntity(any(EquipoDTO.class))).thenReturn(equipo);
        when(mapper.toDTO(equipo)).thenReturn(equipoDTO);

        EquipoDTO result = service.create(equipoDTO);

        assertNotNull(result);
        assertEquals("Manchester United", result.getNombre());
        verify(repository).save(any(Equipo.class));
    }

    @Test
    void testUpdateEquipoExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(equipo));
        doNothing().when(mapper).updateEntityFromDTO(equipoDTO, equipo);
        when(repository.save(equipo)).thenReturn(equipo);
        when(mapper.toDTO(equipo)).thenReturn(equipoDTO);

        EquipoDTO result = service.update(1L, equipoDTO);

        assertNotNull(result);
        assertEquals("Manchester United", result.getNombre());
        verify(repository).findById(1L);
        verify(repository).save(equipo);
    }

    @Test
    void testUpdateEquipoNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EquipoNotFoundException.class, () -> service.update(1L, equipoDTO));

        verify(repository).findById(1L);
    }

    @Test
    void testDeleteByIdEquipoExists() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteByIdEquipoNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EquipoNotFoundException.class, () -> service.deleteById(1L));

        verify(repository).existsById(1L);
    }
}
