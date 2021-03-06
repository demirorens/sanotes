package com.sanotes.web.controller;

import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.NotesVersionModel;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.payload.NoteRequest;
import com.sanotes.web.payload.NoteResponse;
import com.sanotes.web.security.CurrentUser;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/note")
@Tag(name = "notes", description = "the Note API")
public class NotesController {

    @Autowired
    NotesService notesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> addNote(
            @Parameter(description = "Note parameters", required = true)
            @Valid @RequestBody NoteRequest note,
            @CurrentUser UserPrincipal userPrincipal) {
        NotesModel notesModel = modelMapper.map(note, NotesModel.class);
        notesModel = notesService.saveNote(notesModel, userPrincipal);
        NoteResponse noteResponse = modelMapper.map(notesModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "Note parameters", required = true)
            @Valid @RequestBody NoteRequest note,
            @CurrentUser UserPrincipal userPrincipal) {
        NotesModel notesModel = modelMapper.map(note, NotesModel.class);
        notesModel = notesService.updateNote(notesModel, userPrincipal);
        NoteResponse noteResponse = modelMapper.map(notesModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> getNote(
            @Parameter(description = "Note id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        NotesModel note = notesService.getNote(id, userPrincipal);
        NoteResponse noteResponse = modelMapper.map(note, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/versions")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get note versions",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<List<NoteResponse>> getNoteVersions(
            @Parameter(description = "Note id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        List<NotesVersionModel> notesVersions = notesService.getNoteVersions(id, userPrincipal);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(notesVersions, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<ApiResponse> deleteNote(
            @Parameter(description = "Note id", required = true)
            @Valid @RequestBody ByIdRequest byIdRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        ApiResponse apiResponse = notesService.deleteNote(byIdRequest, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
