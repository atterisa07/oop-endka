public class Student {
    private int sid;
    private String firstname;
    private String lastname;

    public Student(int sid, String firstname, String lastname) {
        this.sid = sid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Student{ID: " + sid + ", Name: " + firstname + " " + lastname + "}";
    }
}
