package kcanmin.com.semiclone.domain.dto.user;

import kcanmin.com.semiclone.domain.entity.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {
  private String pw;
  private String nickName;

}
