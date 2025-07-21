package kcanmin.com.semiclone.domain.entity.post;

import jakarta.persistence.*;
import kcanmin.com.semiclone.domain.entity.user.User;
import kcanmin.com.semiclone.domain.dto.post.PostLikeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_post_like")
@IdClass(PostLikeId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PostLike {
  @Id
  @ManyToOne
  @JoinColumn(name = "pno")
  private Post post;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
