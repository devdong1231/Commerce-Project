public class Customer {
    private String customerName, email;
    private CustomerGrade grade;

    Customer(String name, String email) {
        this.customerName = name;
        this.email = email;
        grade = CustomerGrade.BRONZE;
    }

    public String getName() {
        return customerName;
    }

    public void setName(String name) {
        this.customerName = name;
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
