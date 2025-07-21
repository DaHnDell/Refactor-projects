package entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
  private List<Book> cart = new ArrayList<Book>(); // 장바구니

  public List<Book> getCart() {
    return cart;
  }

  public void setCarts(List<Book> carts) {
    this.cart = cart;
  }

  public List<Book> getBookCount() {
    // TODO Auto-generated method stub
    return null;
  }
}
