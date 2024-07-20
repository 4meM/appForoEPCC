package com.app.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.domain.post.Answer;
import com.app.domain.post.Comment;
import com.app.domain.post.Entry;
import com.app.domain.post.Post;
import com.app.domain.user.ForoUser;
import com.app.repositories.CommentRepositoryImp;
import com.app.services.interfaces.ICommentService;



@Service
public class CommentService implements ICommentService{
    
  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;
  
  @Autowired
  private AnswerService answerService;

  @Autowired
  private CommentRepositoryImp commentRepository;


  @Override
  public Comment postComment(Long postId, Long userId, String content) {
      try {
          Post post = postService.getPostById(postId);
          Entry entry = post.getEntry();
          ForoUser user = userService.getUserbyId(userId);


          Comment comment = new Comment();
          comment.setUser(user);
          comment.setEntry(entry);
          comment.setContent(content);

          return commentRepository.save(comment);
      } catch (Exception e) {
          throw new RuntimeException("No se pudo crear el comentario al post", e);
      }
  }

  @Override
  public Comment answerComment(Long answerId, Long userId, String content) {
      try {
          Answer answer = answerService.getAnswerById(answerId);
          Entry entry = answer.getEntry();
          ForoUser user = userService.getUserbyId(userId);


          Comment comment = new Comment();
          comment.setEntry(entry);
          comment.setUser(user);
          comment.setContent(content);

          return commentRepository.save(comment);
      } catch (Exception e) {
          throw new RuntimeException("No se pudo crear el comentario al answer", e);
      }
  }


}
