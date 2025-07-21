package kcanmin.com.semiclone.domain.taboo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_taboo")
public class Taboo {
  @Id
  @Column(name = "key_word")
  private String keyWord;

  @Column(name = "user_id")
  private String userId;

  private int isuse;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;
}
