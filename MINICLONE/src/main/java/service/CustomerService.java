package service;

import entity.Customer;
import entity.Sale;
import util.Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerService {
  private static CustomerService costomerService = new CustomerService();
  private List<Customer> customers = new ArrayList<Customer>();
  private Customer loginUser;

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  BookService bs = BookService.getInstance();
  SaleService ss = SaleService.getInstance();
  private static final String USER_SER_PATH = "users.ser";

  private CustomerService() {
    // TODO Auto-generated constructor stub
  }

  public static CustomerService getInstance() {
    return costomerService;
  }

  public Customer getLoggedInUser() {
    return loginUser;
  }

  {
    File file = new File(USER_SER_PATH);
    if (!file.exists()) {
      System.out.println("SYSTEM :: [users.ser] 파일이 존재하지 않습니다. 더미 데이터를 초기화합니다.");
      Customer customer = new Customer("id1", "pw1", "미니미니");
      customers.add(customer);
      System.out.println("SYSTEM :: 초기화 더미 데이터 처리 완료.");
      save(); // 초기화 후 바로 저장
    } else {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        customers = (List<Customer>) ois.readObject();
        System.out.println("SYSTEM :: 사용자 데이터 로딩 완료 (" + customers.size() + "명)");
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("SYSTEM :: 사용자 데이터 로드 실패");
        e.printStackTrace();
      }
    }
  }

  //
  // 로그인
  public void login() {

    String id = Utils.next("ID", String.class);
    String pw = Utils.next("PW", String.class);

    if (findBy(id) == null) {
      System.out.println("해당하는 아이디가 없습니다");
      return;

    } else {
      for (Customer c : customers) {
        if (c.getId().equals(id) && c.getPw().equals(pw)) {
          loginUser = c;
          afterLogin();
          return;
        }
      }
      System.out.println("비밀번호 불일치");
      return;

    }
  }

  // 로그인 후
  public void afterLogin() {
    ss.setCustomerService();
    System.out.println(loginUser.getName() + "님. 원하시는 항목을 선택하세요");
    int input = Utils.next("1.도서 검색  2.마이페이지  3. 로그아웃 ", Integer.class, t -> t >= 1 && t <= 3, "1에서 3 사이의 수");
    switch (input) {
      case 1:
        bs.bookSearcher();
        break;
      case 2:
        myPage();
        break;
      case 3:
        logout();
        break;
      default:
        break;
    }
  }


  //로그아웃
  public void logout() {
    loginUser = null;
  }

  // 아이디 생성

  public void customerAdd() {
    System.out.println("회원가입 화면입니다.");
    String id = Utils.next("ID를 입력하세요. 10자 이하의 영문 소문자, 숫자로 구성", String.class, s -> findBy(s) == null && s.matches("^[a-z0-9]{1,10}$"), "중복아이디 존재하거나 형식에 맞지 않습니다");
    String pw = Utils.next("PW를 입력하세요. 8자 이상의 영문 대문자, 영문 소문자, 숫자, 특수기호 조합", String.class, s -> s.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$"), "비밀번호 조건에 맞지 않습니다");
    String name = Utils.next("이름을 입력하세요. 한글 2~4글자", String.class, s -> s.matches("^[가-힣]{2,4}"), "한글로 2~4글자로 입력하세요");

    Customer c = new Customer(id, pw, name);
    customers.add(c);
    System.out.println("ID(" + id + ") PASSWORD(" + pw + ") 생성 완료");

    save();

  }


  // 마이페이지 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void myPage() {
    System.out.println("****마이페이지****");
    while (loginUser != null) {
      int input = Utils.next("1. 구매이력 확인 2. 비밀번호 변경 3. 회원 삭제 4. 뒤로가기 ", Integer.class, t -> t >= 1 && t <= 4,
              "1에서 4 사이의 수");
      switch (input) {
        case 1:
          purchaseList();
          break;
        case 2:
          customerModify();
          break;
        case 3:
          customerRemove();
          break;
        case 4:
          return;
        default:
          break;
      }
    }
  }

  //회원삭제
  public void customerRemove() {
    System.out.println("삭제를 원하시면 본인의 비밀번호를 입력하세요");
    String pw = Utils.next
            ("PW", String.class);
    if (loginUser.getPw().equals(pw)) {
      customers.remove(loginUser);
      loginUser = null;
      save();
      System.out.println("삭제완료");
      return;
    }
    System.out.println("비밀번호가 틀렸습니다");
    return;
  }

  //비밀번호 수정
  public void customerModify() {
    String pw = Utils.next("수정할 비밀번호를 입력하세요", String.class, s -> !s.equals(loginUser.getPw()), "현재 본인 비밀번호와 같습니다. 다시 입력해주세요");
    loginUser.setPw(pw);
    System.out.println(pw + "로 수정되었습니다");
    save();
  }


  // 중복체크메서드(객체반환)
  private Customer findBy(String id) {
    Customer customer = null;
    for (int i = 0; i < customers.size(); i++) {
      if (customers.get(i).getId().equals(id)) {
        customer = customers.get(i);
      }
    }
    return customer;
  }


  //구매이력확인
  public void purchaseList() {

    for (Sale s : SaleService.getInstance().getMySale()) {
      System.out.println(s);
      System.out.println(s.getBooks());
    }


  }


  //관리자페이지////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void manager() {
    System.out.println("****관리자 페이지****");
    String key = Utils.next("관리자 키를 입력하세요", String.class);
    while (true) {
      if (key.equals("abcd")) {
        int input = Utils.next("1. 매출확인 2. 책정보 변경 3. 회원리스트 4. 환불 5. 뒤로가기 ", Integer.class, t -> t >= 1 && t <= 5, "1에서 5 사이의 수");
        switch (input) {
          case 1:
            profit();
            break;
          case 2:
            bs.bookAlter();
            break;
          case 3:
            printCustomer();
            break;
          case 4:
            refund();
            break;
          case 5:
            return;
          default:
            break;
        }
      } else {
        System.out.println("관리자 키 불일치. 접근불가");
        return;
      }
    }
  }


  //매출확인
  public void profit() {
    int sum = 0;
    for (Sale s : SaleService.getInstance().getSales()) {
      sum += s.total();
    }
    System.out.println("총 매출 내역 : " + sum + "원");
  }

  // 회원리스트 출력
  public void printCustomer() {
    System.out.println("=====================================");
    System.out.println("         ID         PASSWORD      이름");
    System.out.println("=====================================");
    for (Customer c : customers) {
      System.out.printf("%11s %15s %8s", c.getId(), c.getPw(), c.getName());
      System.out.println();
    }
  }


  public void refund() {
    for (Sale s : ss.getSales()) {
      System.out.println("구매번호 : " + s.getSaleId() + " / 결제 금액 : " + s.total() + " / ID : " + s.getId() + " / 시간 : " + sdf.format(new Date(s.getRegDate())));
      s.total();
      System.out.println(s.getBooks());
    }
    ss.remove();
    System.out.println("환불완료");

  }


  //영속화?
  public void save() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_SER_PATH))) {
      oos.writeObject(customers);
    } catch (IOException e) {
      System.out.println("SYSTEM :: 사용자 저장 실패");
      e.printStackTrace();
    }
  }
}
