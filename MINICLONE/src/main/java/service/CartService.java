package service;

import entity.Book;
import entity.Cart;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartService {
  private static CartService CartService = new CartService();

  private CartService() {
  }

  public static CartService getCartService() {
    return CartService;
  }

  SaleService saleService = SaleService.getInstance();

  Cart cart = new Cart();
  public List<Book> cartList = cart.getCart();
  private List<Book> booklist = new ArrayList<Book>();
  BookService sw = BookService.getInstance();

  public void printCart() {
    int cnt = 1;
    for (Book book : cartList) {
      System.out.println(cnt++ + "::" + book.getBookName() + ":::" + book.getBookCount() + "권");
    }

    int sum = cartList.stream().mapToInt(b -> b.getBookCount() * b.getBookPrice()).sum();
    System.out.println("총 금액은" + sum + "원 입니다");
  }

  public void add(Book a) {
    List<Book> list = cart.getCart();
    boolean flag = false;
    Book tmp = null;
    for (Book book : list) {
      if (book.getBookId().equals(a.getBookId())) {
        flag = true;
        tmp = book;
        break;
      }
    }
    if (flag) {
      tmp.increaseBookCount();
    } else {
      try {
        Book clone = a.clone();
        if (clone != null) {
          list.add(clone);
        } else {
          System.err.println("SYSTEM :: Book 복제에 실패했습니다. 장바구니에 추가하지 못했습니다.");
        }
      } catch (Exception e) {
        System.err.println("SYSTEM :: 장바구니 추가 중 오류 발생");
        e.printStackTrace();
      }
    }
  }


  public void remove() { // 장바구니에 서적 삭제
    Book cs = findBy(Utils.next("책 번호를 입력하세요", String.class, n -> findBy(n) != null, "입력한 책은 존재하지 않습니다."));
    cart.getCart().remove(cs);
  }

  public Book findBy(String bookid) {
    Book newcart = null;
    for (Book ss : booklist) {
      if (ss.getBookId() == bookid) {
        newcart = ss;
      }

    }
    return newcart;
  }

  public void printbook() {
    List<Book> blist = new ArrayList<Book>();
    for (Book se : blist) {
      System.out.println(se);

    }

  }

  public void cartlist() {
    // 책 정보
    printCart();

    int input = Utils.next("1.결제 2.수량 변경 3.돌아가기", Integer.class, i -> i <= 3 && i >= 1, "1이상 5이하의 값을 입력하세요");
    switch (input) {
      case 1:
        printCart();
        saleService.add(cart);
        System.out.println("결제완료");
        cart.getCart().clear(); // 장바구니 비우기
        saleService.getMySale();

        break;
      case 2:
        if (cart.getCart().isEmpty()) {
          System.out.println("장바구니가 비었습니다");
          return;
        }
        System.out.println("수량 변경하기");
        modifyAmount();
        System.out.println("수량 변경이 완료되었습니다.");
        break;
      case 3:
        int back = Utils.next("1.로그인 화면 2.책 검색 화면", Integer.class, i -> true,"1~2의 값을 입력해주세요");
        if(back==1) {
          CustomerService.getInstance().login();
        }else {
          BookService.getInstance().bookSearcher();
        }
        break;
      default:
        return;
    }
  }

  public void modifyAmount() {
    printCart();
    Book book = cart.getCart().get(
            Utils.next("수정할 항목입력", Integer.class, i -> i >= 1 && i <= cart.getCart().size(), "올바른 범위의 값 입력")
                    - 1);
    System.out.println(""+book.getBookName()+"입니다.");
    int amount = Utils.next("변경할 수량을 입력", Integer.class, i -> true,
            "올바른 범위의 값 입력");
    System.out.println("수량을 "+amount+" 만큼 변경했습니다." );
    if (amount == 0) {
      cart.getCart().remove(book);
    } else {
      book.setBookCount(amount);
    }
  }
}
