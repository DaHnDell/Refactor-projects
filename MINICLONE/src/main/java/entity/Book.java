package entity;

import java.io.Serializable;

public class Book implements Serializable, Cloneable {

    private String bookId; // 책 번호
    private String bookName; // 책 제목
    private String bookAuthor; // 책 작가
    private String bookISBN; // 책 출판사
    private String bookPublisher; // 책 번호(ISBN 데이터 활용)
    private String bookDetail; // 책 상세설명
    private int bookPrice; // 책 가격
    private int bookCount;

    public Book(String bookId, String bookName, String bookAuthor, String bookISBN, String bookPublisher, String bookDetail, int bookPrice, int bookCount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookISBN = bookISBN;
        this.bookPublisher = bookPublisher;
        this.bookDetail = bookDetail;
        this.bookPrice = bookPrice;
        this.bookCount = bookCount;
        return;
    }

    public String getBookId(){
        return bookId;
    }

    public String getBookName(){
        return bookName;
    }

    public String getBookAuthor(){
        return bookAuthor;
    }

    public String getBookISBN(){
        return bookISBN;
    }

    public String getBookPublisher(){
        return bookPublisher;
    }

    public String getBookDetail(){
        return bookDetail;
    }

    public int getBookPrice(){
        return bookPrice;
    }

    public int getBookCount(){
        return bookCount;
    }

    @Override
    public Book clone() throws CloneNotSupportedException {
        Book book = (Book) super.clone();
        book.bookCount = 1;
        return book;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setISBookNum(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public void increaseBookCount() {
        bookCount++;
    }

    @Override
    public String toString() {
      return    "::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n"
                + " ::  *제목*  [" + bookName + "] \n"
                + " ::  *저자*  [" + bookAuthor + "] , *출판*  [" + bookPublisher + "] , *도서번호 = [" + bookId + " ]| , *가격*  [" + bookPrice + " ]| \n"
                + " ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n";
    }
}
