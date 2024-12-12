package com.springbootprojects.demoJournalApp.repository;
import com.springbootprojects.demoJournalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {
    
}
