import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    List<Product> productList;

    CommerceSystem() {
        productList = new ArrayList<>();
    }

    public void start(Scanner sc) {
        while (true) {
            // 메뉴 출력
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getName() + "\t" + "| " + String.format("%,10d", productList.get(i).getPrice()) + "원 | " + productList.get(i).getDescription());
            }
            System.out.println("0. 종료\t\t\t| 프로그램 종료");

            
            // 사용자 입력
            int select = 0;
            try {
                select = sc.nextInt();
                int size = productList.size();
                if (!(select >= 0 && select <= size)) { // 범위 밖의 숫자 예외 처리
                    System.out.println("목록 범위 내의 숫자를 입력해주세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못 입력하셨습니다.");
                sc.nextLine();
                continue;
            }

            // 입력값 처리
            if (select == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }
        }
    }

    public void addProduct(String name, int price, String description) {
        productList.add(new Product(name, price, description));
    }

}
