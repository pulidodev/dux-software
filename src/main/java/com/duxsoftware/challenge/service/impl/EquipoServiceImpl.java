package com.duxsoftware.challenge.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duxsoftware.challenge.dto.EquipoDTO;
import com.duxsoftware.challenge.exception.EquipoNotFoundException;
import com.duxsoftware.challenge.mapper.EquipoMapper;
import com.duxsoftware.challenge.model.Equipo;
import com.duxsoftware.challenge.repository.IEquipoRepository;
import com.duxsoftware.challenge.service.IEquipoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements IEquipoService {

    private final IEquipoRepository repository;
    private final EquipoMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Equipo no encontrado";

    @Override
    public List<EquipoDTO> getAll() {
        List<Equipo> equipos = repository.findAll();
        if (equipos.isEmpty()) {
            throw new EquipoNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTOList(equipos);
    }

    @Override
    public EquipoDTO getById(Long id) {
        Optional<Equipo> equipo = repository.findById(id);
        if (equipo.isEmpty()) {
            throw new EquipoNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTO(equipo.get());
    }

    @Override
    public List<EquipoDTO> getByNombre(String nombre) {
        Optional<List<Equipo>> equipos = repository.findByNombreContainingIgnoreCase(nombre);
        if (equipos.get().isEmpty()) {
            throw new EquipoNotFoundException(NOT_FOUND_MESSAGE);
        }
        return mapper.toDTOList(equipos.get());
    }

    @Override
    public EquipoDTO create(EquipoDTO equipoDTO) {
        Equipo savedEquipo = repository.save(mapper.toEntity(equipoDTO));
        return mapper.toDTO(savedEquipo);
    }

    @Override
    public EquipoDTO update(Long id, EquipoDTO equipoDTO) {
        Optional<Equipo> existingEquipo = repository.findById(id);
        if (existingEquipo.isEmpty()) {
            throw new EquipoNotFoundException(NOT_FOUND_MESSAGE);
        }

        mapper.updateEntityFromDTO(equipoDTO, existingEquipo.get());
        repository.save(existingEquipo.get());
        return mapper.toDTO(existingEquipo.get());
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EquipoNotFoundException(NOT_FOUND_MESSAGE);
        }
        repository.deleteById(id);
    }
}
