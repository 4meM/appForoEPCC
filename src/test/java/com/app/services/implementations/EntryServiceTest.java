package com.app.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.domain.post.Entry;
import com.app.domain.user.ForoUser;
import com.app.repositories.EntryRepositoryImp;

@ExtendWith(MockitoExtension.class)
  class EntryServiceTest {
  @Mock
  private EntryRepositoryImp entryRepository;

  @InjectMocks
  private EntryService entryService;

  @Test
  void testCreateEntry () {
    String username = "testUser";
    String content = "contenido";

    ForoUser user= new ForoUser();
    user.setUsername(username);

    Entry entry = new Entry();
    entry.setUser(user);
    entry.setContent(content);


    //when 
    when(entryRepository.save(any(Entry.class))).thenReturn(entry);

    //act
    Entry entryCreated = entryService.createEntry(user, content);

    //assert
    assertNotNull(entryCreated);
  }

  @Test
  void testAddCommentToEntry () {
    Long idEntry = 1L;

    Entry entry = new Entry();
    entry.setId(idEntry);

    //when
    when(entryRepository.existsById(idEntry)).thenReturn(true);
    when(entryRepository.findById(idEntry)).thenReturn(Optional.of(entry));

    //Act
    entryService.addCommentToEntry(idEntry);

    //Assert

    assertEquals(1, entry.getComments());
    verify(entryRepository, times(1)).save(entry);
  }
}
