import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰"));
        productList.add(new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰"));
        productList.add(new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북"));
        productList.add(new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰"));

        printMenu(productList);
        int select = numberInput(sc, productList.size() + 1, "잘못 입력했습니다.");

        if(select == 0){
            System.out.println("커머스 플랫폼을 종료합니다.");
        }

        sc.close();
    }

    public static void printMenu(List<Product> productList) {
        System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i).getName() + "\t" + "| " + String.format("%,10d", productList.get(i).getPrice()) + "원 | " + productList.get(i).getDescription());
        }

        System.out.println("0. 종료\t\t\t| 프로그램 종료");
    }

    public static int numberInput(Scanner sc, int endNumber, String message) {
        while (true) {
            try {
                int select = sc.nextInt();
                if (!(select >= 0 && select <= endNumber)) { // 범위 밖의 숫자 예외 처리
                    System.out.println(message);
                    continue;
                }
                return select;
            } catch (InputMismatchException e) {
                System.out.println(message);
                sc.nextLine();
            }
        }

    }
}
