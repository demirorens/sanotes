package com.sanotes.saNotesWeb.Controller;

import com.sanotes.saNotesPostgres.Service.Model.NoteBookModel;
import com.sanotes.saNotesPostgres.Service.Model.TagModel;
import com.sanotes.saNotesWeb.Service.NoteBookService;
import com.sanotes.saNotesWeb.Service.NotesService;
import com.sanotes.saNotesWeb.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NotesRestController {

   @Autowired
   NoteBookService noteBookService;
   @Autowired
   NotesService notesService;
   @Autowired
   TagService tagService;


    @GetMapping("/notebooks")
    public ResponseEntity<Iterable<NoteBookModel>> getNoteBooks(){
        return ResponseEntity.ok().body(noteBookService.getNoteBooks());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagModel>> getTags(){
        return ResponseEntity.ok().body(tagService.getTags());
    }

    @PostMapping("/addnotebook")
    public ResponseEntity<NoteBookModel> addNoteBook(@RequestBody NoteBookModel noteBook){
        return ResponseEntity.ok().body(noteBookService.saveNoteBook(noteBook));
    }

    @PostMapping("/addtag")
    public ResponseEntity<TagModel> addTag(@RequestBody TagModel tag){
        return ResponseEntity.ok().body(tagService.saveTag(tag));
    }


}
