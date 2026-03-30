public enum CustomerGrade {
    BRONZE(0),
    SILVER(0.05),
    GOLD(0.1),
    PLATINUM(0.15);

    private final double grade;

    // 자동 생성
    CustomerGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }
}
