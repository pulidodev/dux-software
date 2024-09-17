package com.duxsoftware.challenge.service;

import java.util.List;

import com.duxsoftware.challenge.dto.EquipoDTO;

public interface IEquipoService {

    public List<EquipoDTO> getAll();

    public EquipoDTO getById(Long id);

    public List<EquipoDTO> getByNombre(String nombre);

    public EquipoDTO create(EquipoDTO equipoDTO);

    public EquipoDTO update(Long id, EquipoDTO equipoDTO);

    public void deleteById(Long id);
}
