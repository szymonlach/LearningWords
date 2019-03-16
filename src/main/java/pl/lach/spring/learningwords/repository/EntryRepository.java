package pl.lach.spring.learningwords.repository;

import pl.lach.spring.learningwords.FileService;
import pl.lach.spring.learningwords.model.Entry;

import java.io.IOException;
import java.util.*;

public class EntryRepository {
    private List<Entry> entries;

    public EntryRepository() {
        FileService fileService = new FileService();
        try {
            this.entries = fileService.readAllFile();
        } catch (IOException e) {
            entries = new ArrayList<>();
        }
    }

    public List<Entry> getAll() {
        return entries;
    }

    public Set<Entry> getRandomEntries(int number) {
        Random random = new Random();
        Set<Entry> randomEntries = new HashSet<>();
        while (randomEntries.size() < number && randomEntries.size() < entries.size()) {
            randomEntries.add(entries.get(random.nextInt(entries.size())));
        }
        return randomEntries;
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public int size() {
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}