package util;

import java.util.Scanner;
import java.util.function.Predicate;

public class Utils {
  static Scanner scanner = new Scanner(System.in);

  public static <T> T next(String msg, Class<T> clazz){
    System.out.println(msg);
    System.out.println(" >> ");
    String str = scanner.nextLine();
    if(clazz == Integer.class){
      return clazz.cast(Integer.parseInt(str));
    } else if (clazz == String.class) {
      return clazz.cast(str);
    } else {
      throw new RuntimeException("잘못된 입력입니다.");
    }
  }

  public static <T> T next(String msg, Class<T> clazz, Predicate<T> tf, String err){
    while (true) {
      try {
        T type = next(msg, clazz);
        if(tf.test(type)){
          return type;
        } else {
          throw new IllegalArgumentException(err);
        }
      } catch (NumberFormatException nfe) {
        System.out.println("올바른 값을 입력하여 주십시오");
      } catch (IllegalArgumentException iae) {
        System.out.println("올바른 값이 아닙니다.");
      }
    }
  }
}
