package service;

import entity.Book;
import jdk.jshell.execution.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import util.Utils;

public class BookService {

    private Calendar calendar = Calendar.getInstance();
    private int month = calendar.get(Calendar.MONTH);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년-MM월-dd일");

    private BookService bookService;
    private static BookService BookService = new BookService();
    public static BookService getInstance() {
        return BookService;
    }
    CartService cartService = CartService.getCartService();
    private List<Book> bookList = new ArrayList<>();


    {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.ser")) {
            if (is == null) {
                System.out.println("SYSTEM :: [data.ser] 파일이 존재하지 않습니다. 초기 도서만 사용됩니다.");
            } else {
                try (ObjectInputStream ois = new ObjectInputStream(is)) {
                    bookList = (List<Book>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("SYSTEM :: 불러오기 오류 발생 - 입출력 오류가 발생했습니다.");
            e.printStackTrace();
        }

    }

    public void bookSearcher() {
        bookEvent(bookList);
        boolean flag = true;
        System.out.print(""
                + "=================================================================================================\n"
                + "SYSTEM :: 얼라들의 알라딘, 얼라딘에서 무엇이든 검색하세요! 현재 날짜는 " + sdf.format(calendar.getTime())
                + "입니다. \n"
                + "=================================================================================================\n");
        String[] categories = { "도서번호", "ISBN", "제목", "저자", "전체", "뒤로", "장바구니" };
        while (true) {
            System.out.print("SYSTEM :: 도서를 검색합니다.  ");
            for (int i = 0; i < categories.length; i++) {
                System.out.printf(" %d.%s", (i + 1), categories[i]);
            }
            System.out.println();
            int input = Utils.next("SYSTEM :: 카테고리 입력", Integer.class, i -> i >= 1 && i <= 7, "1~7 사이의 숫자 입력 :: ");
            switch (input) {
                case 1:
                case 2:
                case 3:
                case 4: {
                    searchBook(input, Utils.next("SYSTEM :: 검색어를 입력하세요. :: ", String.class));
                    break;
                }
                case 5: {
                    printBooks(bookList);
                    break;
                }
                case 6: {
                    System.out.println("SYSTEM :: 이전 메뉴로 돌아갑니다. :: ");
                    return;
                }
                case 7: {
                    System.out.println("SYSTEM :: 장바구니를 불러옵니다. :: ");
                    cartService.cartlist();
                    flag = false;
                    return;
                }
                default:
                    break;
            }
        }
    }

    public void printBooks(List<Book> listTarget) {
        List<Book> pBook = new ArrayList<>(listTarget);
        pBook.sort((p1, p2) -> p1.getBookName().compareTo(p2.getBookName()));
        pBook.forEach(x -> System.out.print(x + "\n"));
    }

    public void searchBook(int search, String target) {
        List<Book> returnList = null;
        switch (search) {
            case 1:
                returnList = findByBookId(target);
                break;
            case 2:
                returnList = findByBookISBN(target);
                break;
            case 3:
                returnList = findByBookName(target);
                break;
            case 4:
                returnList = findByWriter(target);
                break;
            default:
                return;
        }
        if (returnList != null && !returnList.isEmpty()) {
            System.out.println("=========================================== 검색  결과 ===========================================");
            for (int i = 0; i < returnList.size(); i++) {
                System.out.print(" :: " + (i + 1) + " :: ");
                System.out.println(returnList.get(i));
            }
            System.out.println("SYSTEM :: 출력이 완료되었습니다. :: ");
            int sizebook = returnList.size();
            int input2 = Utils.next("SYSTEM :: 번호로 책을 골라주세요", Integer.class, s -> s >= 1 && s <= sizebook,
                    "SYSTEM :: INPUT ERROR :: ");
            System.out.println("SYSTEM :: " + returnList.get(input2 - 1).getBookName() + "의 상세정보를 로드합니다.");
            showBookDetails(returnList.get(input2 - 1));
        } else {
            System.err.println("SYSTEM :: 검색 결과가 없습니다. :: ");
        }
    }

    public List<Book> findByBookId(String no) {
        List<Book> tmp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookId().contains(no)) {
                tmp.add(book);
            }
        }
        return tmp;
    }

    public List<Book> findByBookISBN(String no) {
        List<Book> tmp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookId().contains(no)) {
                tmp.add(book);
            }
        }
        return tmp;

    }

    public List<Book> findByWriter(String writer) {
        List<Book> tmp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookAuthor().contains(writer)) {
                tmp.add(book);
            }
        }
        return tmp;
    }

    public List<Book> findByBookName(String bookName) {
        List<Book> tmp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookName().contains(bookName)) {
                tmp.add(book);
            }
        }
        return tmp;
    }

    public void showBookDetails(Book book) {
        String formattedDesc = wrapText(book.getBookDetail(), 80); // 80자마다 줄바꿈
        System.out.println("*소개 : " + formattedDesc);
        System.out.println("| *정가 : " + book.getBookPrice() + " | 1.장바구니  2.뒤로가기");

        CartService cs = CartService.getCartService();
        int key = Utils.next("입력", Integer.class, i -> i >= 1 && i <= 2, "SYSTEM :: INPUT ERROR :: ");
        switch (key) {
            case 1: {
                if (book.getBookPrice() == 0) {
                    System.out.println("SYSTEM :: 절판된 상품입니다. 구매가 불가합니다. 검색 화면으로 돌아갑니다. :: ");
                    break;
                }
                cs.add(book);
                System.out.println("SYSTEM :: 장바구니에 상품이 담겼습니다. :: ");
                cs.printCart();
                break;
            }
            case 2: {
                System.out.println("SYSTEM :: 초기 화면으로 돌아갑니다. :: ");
                break;
            }
        }
    }


    public void bookEvent(List<Book> listTarget) {
        if (listTarget == null || listTarget.isEmpty()) {
            System.out.println("SYSTEM :: 도서 목록이 비어있습니다. 추천 도서를 제공할 수 없습니다.");
            return;
        }

        int ran = (int) (Math.random() * listTarget.size());
        CustomerService customer = CustomerService.getInstance();
        String name = customer.getLoggedInUser().getName();
        Book book = listTarget.get(ran);
        System.out.println("888     888      888                         888           888      d8b               \n" +
                "888     888      888                         888           888      Y8P               \n" +
                "888     888      888                         888           888                        \n" +
                "888     888      888       8888b.        .d88888       .d88888      888      88888b.  \n" +
                "888     888      888          \"88b      d88\" 888      d88\" 888      888      888 \"88b \n" +
                "888     888      888      .d888888      888  888      888  888      888      888  888 \n" +
                "Y88b. .d88P      888      888  888      Y88b 888      Y88b 888      888      888  888 \n" +
                " \"Y88888P\"       888      \"Y888888       \"Y88888       \"Y88888      888      888  888 \n");
        System.out.println("=================================================================================================");
        System.out.println("SYSTEM :: " + name + " 님, 어서오세요! 아래의 이벤트들을 확인해 보세요! :: ");
        System.out.println("=================================================================================================");
        System.out.println(month + " 월의 얼라딘북스 < " + book.getBookName() + " > \n"
                + month + " 월의 얼라딘터뷰 < " + book.getBookAuthor() + " 작가와의 사색> ");
    }


    public void bookAlter() {
        int input = Utils.next(("1. 책 등록 2. 책 정보 수정 3. 책 삭제 4. 뒤로가기"), Integer.class, i -> i <= 4 && i >= 1,
                "1이상 4이하의 값을 입력하세요");
        List<Book> tmp = null;
        switch (input) {
            case 1:
                bookAdd();
                break;
            case 2:
                bookModify();
                break;
            case 3:
                bookRemove();
                break;
            default:
                return;
        }
    }

    public void bookAdd() {
        String bookId = Utils.next("책 번호(서점용)", String.class, n -> n.length() == 4, "올바른 책 번호를 입력하세요 (4자리의 숫자)");
        String bookName = Utils.next("책 제목", String.class);
        String bookWriter = Utils.next("저자명", String.class);
        String bookPublisher = Utils.next("출판사", String.class);
        String ISBookNum = Utils.next("책 번호(ISBN)", String.class, n -> n.length() == 13,
                "올바른 책 번호를 입력하세요 (13자리의 숫자)");
        String bookDetail = Utils.next("상세설명", String.class);
        int bookPrice = Utils.next("책 가격", Integer.class, n -> true, "올바른 숫자를 입력하세요");
        int bookCount = Utils.next("책 재고", Integer.class, n -> true, "올바른 숫자를 입력하세요");
        boolean b1 = false;
        boolean b2 = false;

        bookList.add(new Book(bookId, bookName, bookWriter, bookPublisher, ISBookNum, bookDetail, bookPrice, bookCount));
    }

    /**
     * 도서 정보 수정
     */

    private Book findBy(String bookId) {
        Book book = null;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId() == bookId) {
                book = bookList.get(i);
            }
        }
        return book;
    }

    public void bookModify() {
        Book b = findBy(Utils.next("책 번호(서점용)", String.class, n -> true, null));
        String bookId = Utils.next("책 번호(서점용)", String.class, n -> n.length() == 4, "올바른 책 번호를 입력하세요 (4자리의 숫자)");
        b.setBookId(bookId);
        String bookName = Utils.next("책 제목", String.class);
        b.setBookName(bookName);
        String bookWriter = Utils.next("저자명", String.class);
        b.setBookAuthor(bookWriter);
        String bookPublisher = Utils.next("출판사", String.class);
        b.setBookPublisher(bookPublisher);
        String ISBookNum = Utils.next("책 번호(ISBN)", String.class, n -> n.length() == 13,
                "올바른 책 번호를 입력하세요 (13자리의 숫자)");
        b.setISBookNum(ISBookNum);
        String bookDetail = Utils.next("상세설명", String.class);
        b.setBookDetail(bookDetail);
        int bookPrice = Utils.next("책 가격", Integer.class);
        b.setBookPrice(bookPrice);
        int bookCount = Utils.next("책 재고", Integer.class);
        b.setBookCount(bookCount);
        boolean b1 = false;
        boolean b2 = false;
        bookList.add(new Book(bookId, bookName, bookWriter, bookPublisher, ISBookNum, bookDetail, bookPrice, bookCount));
    }

    /**
     * 도서 삭제
     *
     * @author HHJ
     */
    public void bookRemove() {
        Book b = findBy(Utils.next("책 번호", String.class, n -> findBy(n) != null, "입력한 책 번호는 존재하지 않습니다"));
        bookList.remove(b);
    }

    public static String wrapText(String text, int lineLength) {
        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < text.length()) {
            int endIndex = Math.min(index + lineLength, text.length());
            result.append(text, index, endIndex).append("\n");
            index += lineLength;
        }

        return result.toString();
    }
}
