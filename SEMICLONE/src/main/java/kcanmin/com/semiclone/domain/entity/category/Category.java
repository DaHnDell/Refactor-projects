package kcanmin.com.semiclone.domain.entity.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;

  private String cname;

  @Column(name = "main_category")
  private String mainCategory;

  @Column(name = "sub_category")
  private String subCategory;

  @Column(name = "parent_cno")
  private Long parentCno;

  private int sort;

  @Column(name = "isuse")
  private String isUse;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  private String icon;
}
