package kcanmin.com.semiclone.dto.manage;

public class Statistics {
  private String target;
  private int count;

  public Statistics() {}

  public Statistics(String target, int count) {
    this.target = target;
    this.count = count;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
