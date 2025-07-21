package kcanmin.com.semiclone.domain.dto.manage;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
  private String target;
  private int count;

}
