package kcanmin.com.semiclone.domain.post;

import jakarta.persistence.*;
import kcanmin.com.semiclone.domain.user.User;

@Entity
@Table(name = "tbl_post_like")
@IdClass(PostLikeId.class)
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
