package com.springbootprojects.demoJournalApp.services;
import com.springbootprojects.demoJournalApp.entity.JournalEntry;
import com.springbootprojects.demoJournalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry ){
        journalEntryRepository.save(journalEntry);
    }

}
