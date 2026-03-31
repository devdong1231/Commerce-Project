public class Customer {
    private String name, email;
    private CustomerGrade grade;

    Customer(String name, String email) {
        this.name = name;
        this.email = email;
        grade = CustomerGrade.BRONZE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerGrade getGrade() {
        return grade;
    }

    public void setGrade(CustomerGrade grade) {
        this.grade = grade;
    }

}
