package com.app.services.interfaces;

import java.util.List;

import com.app.domain.post.Post;

public interface IPostService {
  public Post createPost (Long id_user,String title,String content);

  public boolean index();
  
  public List<Post> searchWord(String query);

  //public Post updatePost(Long id_post,Post modifiedPost);
  
}