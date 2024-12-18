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

    @DeleteMapping("id/{userId}")
    public String deleteEntry(@PathVariable ObjectId userId){
        return journalEntryService.deleteEntry(userId);
    }


    /**
     * Updates an existing journal entry identified by the userId. If the journal entry
     * exists, certain fields are updated based on the provided userUpdatedEntry.
     *
     * @param userId the unique identifier of the journal entry to update
     * @param userUpdatedEntry the journal entry data containing updated fields
     * @return a message indicating the result of the update operation
     */

    @PutMapping("id/{userId}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable ObjectId userId,
            @RequestBody JournalEntry userUpdatedEntry) {

        // Retrieve and validate initial entry
        JournalEntry userInitialEntry = journalEntryService.findById(userId).orElse(null);
        if (userInitialEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update fields if non-null and non-empty
        if (userUpdatedEntry.getTitle() != null && !userUpdatedEntry.getTitle().isEmpty()) {
            userInitialEntry.setTitle(userUpdatedEntry.getTitle());
        }
        if (userUpdatedEntry.getContent() != null && !userUpdatedEntry.getContent().isEmpty()) {
            userInitialEntry.setContent(userUpdatedEntry.getContent());
        }

        // Set updated date
        userInitialEntry.setDate(LocalDateTime.now());

        // Save updated entry and handle errors
        try {
            journalEntryService.saveEntry(userInitialEntry);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return updated entry
        return new ResponseEntity<>(userInitialEntry, HttpStatus.OK);
    }

    @GetMapping("id/{userId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId userId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(userId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
