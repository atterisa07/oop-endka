public class Book {
    private int bid;
    private String title;
    private String author;
    private int pages;

    public Book(int bid, String title, String author, int pages) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Book{ID: " + bid + ", Title: " + title + ", Author: " + author + ", Pages: " + pages + "}";
    }
}
