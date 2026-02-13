public class Loan {
    private int lid;
    private int sid;
    private int bid;
    private String loanDate;
    private String returnDate;

    public Loan(int lid, int sid, int bid, String loanDate, String returnDate) {
        this.lid = lid;
        this.sid = sid;
        this.bid = bid;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        String status = returnDate == null ? "Not returned" : "Returned on " + returnDate;
        return "Loan{ID: " + lid + ", Student ID: " + sid + ", Book ID: " + bid + 
               ", Loan Date: " + loanDate + ", " + status + "}";
    }
}
