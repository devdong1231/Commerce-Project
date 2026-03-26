import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommerceSystem commerceSystem = new CommerceSystem();

        commerceSystem.addProduct("Galaxy S25", 1200000, "최신 안드로이드 스마트폰");
        commerceSystem.addProduct("iPhone 16", 1350000, "Apple의 최신 스마트폰");
        commerceSystem.addProduct("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북");
        commerceSystem.addProduct("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰");

        commerceSystem.start(sc);


        sc.close();
    }
}
