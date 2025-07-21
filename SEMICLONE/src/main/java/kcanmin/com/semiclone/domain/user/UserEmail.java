package kcanmin.com.semiclone.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_user_email")
public class UserEmail {
  @Id
  private String email;

  private String att;
}
