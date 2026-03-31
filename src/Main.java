import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommerceSystem commerceSystem = new CommerceSystem("asdf");
        Category electronic = new Category("전자제품");
        electronic.addProduct("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 1);
        electronic.addProduct("iPhone 15", 1350000, "Apple의 최신 스마트폰", 30);
        electronic.addProduct("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 15);
        electronic.addProduct("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 50);
        commerceSystem.addCategory(electronic);
        commerceSystem.addCategory(new Category("의류"));
        commerceSystem.addCategory(new Category("식품"));

        commerceSystem.start();

        IOHandler.closeScanner();
    }
}
