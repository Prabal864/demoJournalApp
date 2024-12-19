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
     * Updates an existing journal entry for the specified user ID with the provided updated information.
     * Retrieves the current entry by the user ID, validates it, and applies any non-null and non-empty changes.
     * Saves the updated entry to the database and returns it.
     *
     * @param userId the unique identifier of the user whose journal entry needs to be updated
     * @param userUpdatedEntry a JournalEntry object containing the updated title and/or content
     * @return a ResponseEntity containing the updated JournalEntry if successful, or an appropriate HTTP status code if the entry couldn't be found or an error occurred
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
