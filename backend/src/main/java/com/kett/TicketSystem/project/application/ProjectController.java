package com.kett.TicketSystem.project.application;

import com.kett.TicketSystem.application.TicketSystemService;

import com.kett.TicketSystem.project.application.dto.*;
import com.kett.TicketSystem.project.domain.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional
@CrossOrigin(origins = {"http://127.0.0.1:5173"})
@RequestMapping("/projects")
public class ProjectController {
    private final TicketSystemService ticketSystemService;

    @Autowired
    public ProjectController(TicketSystemService ticketSystemService) {
        this.ticketSystemService = ticketSystemService;
    }


    // rest endpoints

    // TODO: only for testing
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjectById() {
        List<ProjectResponseDto> projectResponseDto = ticketSystemService.fetchAllProjects();
        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable UUID id) {
        ProjectResponseDto projectResponseDto = ticketSystemService.fetchProjectById(id);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> postProject(@RequestBody ProjectPostDto projectPostDto) {
        ProjectResponseDto projectResponseDto = ticketSystemService.addProject(projectPostDto);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(projectResponseDto.getId())
                .toUri();

        return ResponseEntity
                .created(returnURI)
                .body(projectResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchProjectById(@PathVariable UUID id, @RequestBody ProjectPatchDto projectPatchDto) {
        ticketSystemService.patchProjectById(id, projectPatchDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable UUID id) { // TODO: What to use instead of Object?
        ticketSystemService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // exception handlers

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException projectException) {
        return new ResponseEntity<>(projectException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoProjectFoundException.class)
    public ResponseEntity<String> handleNoProjectFoundException(NoProjectFoundException noProjectFoundException) {
        return new ResponseEntity<>(noProjectFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImpossibleException.class)
    public ResponseEntity<String> handleImpossibleException(ImpossibleException impossibleException) {
        return new ResponseEntity<>(impossibleException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
