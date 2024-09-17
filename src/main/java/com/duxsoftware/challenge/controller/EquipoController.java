package com.duxsoftware.challenge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duxsoftware.challenge.dto.EquipoDTO;
import com.duxsoftware.challenge.service.IEquipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/equipos")
@Validated
@RequiredArgsConstructor
@Tag(name = "Teams", description = "Operations related to team management, such as retrieving, creating, updating, and deleting teams.")
public class EquipoController {

        private final IEquipoService service;

        @Operation(summary = "Get all teams", description = "Retrieve a list of all teams. This operation returns a full list of teams available in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of teams"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping
        public ResponseEntity<List<EquipoDTO>> getAll() {
                return ResponseEntity.ok(service.getAll());
        }

        @Operation(summary = "Get a team by ID", description = "Retrieve a specific team by its ID. The team ID must be valid and exist in the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the team"),
                        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/{id}")
        public ResponseEntity<EquipoDTO> getById(
                        @PathVariable @Parameter(description = "ID of the team to retrieve") Long id) {
                return ResponseEntity.ok(service.getById(id));
        }

        @Operation(summary = "Search teams by name", description = "Search for teams that match the provided name. This is a case-insensitive search.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved matching teams"),
                        @ApiResponse(responseCode = "400", description = "Invalid name parameter"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/buscar")
        public ResponseEntity<List<EquipoDTO>> getByNombre(
                        @RequestParam @Parameter(description = "Name of the team to search", example = "Madrid") String nombre) {
                return ResponseEntity.ok(service.getByNombre(nombre));
        }

        @Operation(summary = "Create a new team", description = "Create a new team with the provided details. Ensure all required fields are filled and valid.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Successfully created the team"),
                        @ApiResponse(responseCode = "400", description = "La solicitud es invalida"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @PostMapping
        public ResponseEntity<EquipoDTO> create(
                        @Valid @RequestBody @Parameter(description = "Details of the team to create") EquipoDTO equipoDTO) {
                return ResponseEntity.status(HttpStatus.CREATED).body(service.create(equipoDTO));
        }

        @Operation(summary = "Update an existing team", description = "Update the details of a specific team. The team ID must exist, and the provided data must be valid.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully updated the team"),
                        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
                        @ApiResponse(responseCode = "400", description = "La solicitud es invalida"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @PutMapping("/{id}")
        public ResponseEntity<EquipoDTO> update(
                        @PathVariable @Parameter(description = "ID of the team to update") Long id,
                        @Valid @RequestBody @Parameter(description = "Updated details of the team") EquipoDTO equipoDTO) {
                return ResponseEntity.ok(service.update(id, equipoDTO));
        }

        @Operation(summary = "Delete a team by ID", description = "Delete a specific team by its ID. This action is irreversible.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Successfully deleted the team"),
                        @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(
                        @PathVariable @Parameter(description = "ID of the team to delete") Long id) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
        }
}
