package com.duxsoftware.challenge.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.duxsoftware.challenge.dto.EquipoDTO;
import com.duxsoftware.challenge.model.Equipo;

@Component
public class EquipoMapper {

    public EquipoDTO toDTO(Equipo equipo) {
        return EquipoDTO.builder()
                .id(equipo.getId())
                .nombre(equipo.getNombre())
                .liga(equipo.getLiga())
                .pais(equipo.getPais())
                .build();
    }

    public Equipo toEntity(EquipoDTO equipoDTO) {
        return Equipo.builder()
                .nombre(equipoDTO.getNombre())
                .liga(equipoDTO.getLiga())
                .pais(equipoDTO.getPais())
                .build();
    }

    public List<EquipoDTO> toDTOList(List<Equipo> equipos) {
        return equipos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(EquipoDTO equipoDTO, Equipo equipo) {
        if (equipoDTO.getNombre() != null) {
            equipo.setNombre(equipoDTO.getNombre());
        }
        if (equipoDTO.getLiga() != null) {
            equipo.setLiga(equipoDTO.getLiga());
        }
        if (equipoDTO.getPais() != null) {
            equipo.setPais(equipoDTO.getPais());
        }
    }
}