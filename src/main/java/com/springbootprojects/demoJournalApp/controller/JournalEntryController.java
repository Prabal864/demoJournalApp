package com.springbootprojects.demoJournalApp.controller;
import com.springbootprojects.demoJournalApp.entity.JournalEntry;
import com.springbootprojects.demoJournalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public void getAll(){

    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry userEntry){
        journalEntryService.saveEntry(userEntry);
        return "Entry Created";
    }
}
