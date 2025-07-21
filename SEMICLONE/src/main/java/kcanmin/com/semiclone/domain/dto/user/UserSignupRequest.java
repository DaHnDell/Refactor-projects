package kcanmin.com.semiclone.domain.dto.user;

import kcanmin.com.semiclone.domain.entity.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {
  private String id;
  private String pw;
  private String nickName;

  public User toEntity() {
    return User.builder()
            .id(id)
            .pw(pw)
            .nickName(nickName)
            .build();
  }
}
