package kcanmin.com.semiclone.domain.post;

import jakarta.persistence.*;
import kcanmin.com.semiclone.domain.category.Category;
import kcanmin.com.semiclone.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pno;

  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String content;

  @Column(name = "view_cnt")
  private int viewCnt;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @ManyToOne
  @JoinColumn(name = "cno")
  private Category category;

  private String imgData;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<PostLike> likes;

}
