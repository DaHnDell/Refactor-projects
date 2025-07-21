package kcanmin.com.semiclone.domain.user;

import jakarta.persistence.*;
import kcanmin.com.semiclone.domain.post.Post;
import kcanmin.com.semiclone.domain.post.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {

  @Id
  private String id;

  private String pw;

  @Column(name = "nick_name")
  private String nickName;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserDetail userDetail;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Post> posts;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<PostLike> postLikes;
}
