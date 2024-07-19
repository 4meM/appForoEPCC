package com.app.services.interfaces;

import com.app.domain.post.Answer;

public interface IAnswerService {

  public Answer createAnswer(Long post_id_to_reply,Long id_user,String content);

  public Answer getAnswerById(Long id_answer);

}