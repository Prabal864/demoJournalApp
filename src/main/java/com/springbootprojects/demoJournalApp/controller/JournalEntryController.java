package com.springbootprojects.demoJournalApp.controller;
import com.springbootprojects.demoJournalApp.entity.JournalEntry;
import com.springbootprojects.demoJournalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll();
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry userEntry){
        journalEntryService.saveEntry(userEntry);
        userEntry.setDate(LocalDateTime.now());
        return "Entry Created";
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId userId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(userId);
        if (journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
