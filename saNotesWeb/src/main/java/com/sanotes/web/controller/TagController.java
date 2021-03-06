package com.sanotes.web.controller;

import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.TagModel;
import com.sanotes.web.payload.*;
import com.sanotes.web.security.CurrentUser;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.TagService;
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
@RequestMapping("/api/v1/tag")
@Tag(name = "tag", description = "the Tag API")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add tag",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"tag"})
    public ResponseEntity<TagResponse> addTag(
            @Parameter(description = "Tag parameters", required = true)
            @Valid @RequestBody TagRequest tag,
            @CurrentUser UserPrincipal userPrincipal) {
        TagModel tagModel = new TagModel(tag.getName(), tag.getDescription());
        tagModel = tagService.saveTag(tagModel, userPrincipal);
        TagResponse tagResponse = modelMapper.map(tagModel, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update tag",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"tag"})
    public ResponseEntity<TagResponse> updateTag(
            @Parameter(description = "Tag parameters", required = true)
            @Valid @RequestBody TagRequest tag,
            @CurrentUser UserPrincipal userPrincipal) {
        TagModel tagModel = new TagModel(tag.getName(), tag.getDescription());
        tagModel.setId(tag.getId());
        tagModel = tagService.updateTag(tagModel, userPrincipal);
        TagResponse tagResponse = modelMapper.map(tagModel, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get tag and related notes",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"tag"})
    public ResponseEntity<List<NoteResponse>> getTagNotes(
            @Parameter(description = "Tag id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        List<NotesModel> notes = tagService.getNotes(id, userPrincipal);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(notes, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete tag",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"tag"})
    public ResponseEntity<ApiResponse> deleteTag(
            @Parameter(description = "Tag id", required = true)
            @Valid @RequestBody ByIdRequest byIdRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        ApiResponse apiResponse = tagService.deleteTag(byIdRequest, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
