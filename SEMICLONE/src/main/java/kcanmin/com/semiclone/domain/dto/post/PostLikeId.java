package kcanmin.com.semiclone.domain.dto.post;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeId {
  private Long post;
  private String user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PostLikeId)) return false;
    PostLikeId that = (PostLikeId) o;
    return Objects.equals(post, that.post) &&
            Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(post, user);
  }
}
