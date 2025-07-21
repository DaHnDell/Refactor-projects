package kcanmin.com.semiclone.dto.user;

import kcanmin.com.semiclone.domain.user.User;

public class UserResponse {
  private final String id;
  private final String nickName;

  public UserResponse(User user) {
    this.id = user.getId();
    this.nickName = user.getNickName();
  }
}
