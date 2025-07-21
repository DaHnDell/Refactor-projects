package kcanmin.com.semiclone.dto.user;

import kcanmin.com.semiclone.domain.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class UserSignupRequest {
  private String id;
  private String pw;
  private String nickName;

  @Builder
  public UserSignupRequest(String id, String pw, String nickName) {
    this.id = id;
    this.pw = pw;
    this.nickName = nickName;
  }

  public User toEntity() {
    return User.builder()
            .id(id)
            .pw(pw)
            .nickName(nickName)
            .build();
  }
}
