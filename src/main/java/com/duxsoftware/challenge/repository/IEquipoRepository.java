package com.duxsoftware.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duxsoftware.challenge.model.Equipo;

@Repository
public interface IEquipoRepository extends JpaRepository<Equipo, Long> {

    public Optional<List<Equipo>> findByNombreContainingIgnoreCase(String nombre);
}
