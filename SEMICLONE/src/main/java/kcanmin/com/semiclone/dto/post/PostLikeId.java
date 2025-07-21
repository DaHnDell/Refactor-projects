package kcanmin.com.semiclone.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
public class PostLikeId {
  private Long post;
  private String user;

  public PostLikeId() {}

  public PostLikeId(Long post, String user) {
    this.post = post;
    this.user = user;
  }

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
