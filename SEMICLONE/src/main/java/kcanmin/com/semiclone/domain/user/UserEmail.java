package kcanmin.com.semiclone.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user_email")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserEmail {
  @Id
  private String email;

  private String att;
}
