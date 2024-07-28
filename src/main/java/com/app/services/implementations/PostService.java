package com.app.services.implementations;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.exceptions.CreationException;
import com.app.repositories.PostRepositoryImp;
import com.app.services.interfaces.IPostService;



@Service
public class PostService implements IPostService{

  private EntryService entryService;
  private PostRepositoryImp postRepository;
  private UserService userService;

  public PostService (EntryService entryService, PostRepositoryImp postRepository ,UserService userService) {
    this.entryService = entryService;
    this.postRepository = postRepository;
    this.userService = userService;

  }

  @Override
  @Transactional
  public Post createPost(Long idUser,String title,String content){
    try {
      String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
      ForoUser userFound = userService.getUserByUsername(user);
      Entry entrySaved = entryService.createEntry(userFound, content);

      Post postCreated = new Post();
      postCreated.setEntry(entrySaved);
      postCreated.setTitle(title);

      return postRepository.save(postCreated);
      
    } catch(Exception e){
      throw new CreationException("No se pudo crear el post");
    }
  }

  @Transactional(readOnly = true)
  public Post getPostById(Long idPost){
    return postRepository.findById(idPost)
      .orElseThrow();
  }

}