package io.javabrains.springbootstarter.bean;

import java.util.List;

public class UserBook {
    private int userId;
    //issued book id
    private List<Integer> bookIssued;
    private List<Integer> bookReserved;




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getBookIssued() {
        return bookIssued;
    }

    public void setBookIssued(List<Integer> bookIssued) {
        this.bookIssued = bookIssued;
    }

    public List<Integer> getBookReserved() {
        return bookReserved;
    }

    public void setBookReserved(List<Integer> bookReserved) {
        this.bookReserved = bookReserved;
    }
}
