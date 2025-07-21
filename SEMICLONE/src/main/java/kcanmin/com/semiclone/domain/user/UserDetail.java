package kcanmin.com.semiclone.domain.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user_detail")
public class UserDetail {
  @Id
  private String id;

  private String name;
  private String gender;
  private String addr;

  @Column(name = "detail_addr")
  private String detailAddr;

  @Column(name = "self_intro")
  private String selfIntro;

  private String grade;
  private String mtno;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @OneToOne
  @JoinColumn(name = "id")
  private User user;
}
