package com.app.services.implementations.post;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;
import com.app.repositories.post.EntryRepositoryImp;
import com.app.services.interfaces.post.IEntryService;

@Service
public class EntryService implements IEntryService {

    private final EntryRepositoryImp entryRepository;

    @Autowired
    public EntryService(EntryRepositoryImp entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    @Transactional
    public Entry createEntry(ForoUser user, String content) {
        try {
            Entry entryCreated = new Entry();
            entryCreated.setUser(user);
            entryCreated.setContent(content);

            return entryRepository.save(entryCreated);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo crear el entry", e);
        }
    }

    @Override
    @Transactional
    public void addCommentToEntry(Long idEntry) {
        if (!entryRepository.existsById(idEntry)) {
            throw new NoSuchElementException("No se puede añadir el contador de comentarios");
        }
        Optional<Entry> entryO = entryRepository.findById(idEntry);
        if (entryO.isPresent()) {
            Entry entry = entryO.get();
            entry.setComments(entry.getComments() + 1);
            entryRepository.save(entry);
        } else {
            throw new NoSuchElementException("Entry no encontrado");
        }
    }
}