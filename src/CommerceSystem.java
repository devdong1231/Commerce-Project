import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private List<Category> categories;

    CommerceSystem() {
        categories = new ArrayList<>();
    }

    public void start() {
        while (true) {
            int select = 0;
            Category category = null;
            Product product = null;

            // 카테고리 출력
            System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getCategory());
            }
            System.out.println("0. 종료\t| 프로그램 종료");


            // 카테고리 입력
            select = inputNumber(categories.size());
            if (select != 0)
                category = categories.get(select - 1);


            // 0번 선택
            if (select == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }


            // 다른 번호 선택
            // 메뉴 출력
            System.out.println("[ " + category.getCategory() + " 카테고리 ]");
            for (int i = 0; i < category.getProductList().size(); i++) {
                System.out.println((i + 1) + ". " + category.getProductList().get(i).getName() + "\t" + "| " + String.format("%,10d", category.getProductList().get(i).getPrice()) + "원 | " + category.getProductList().get(i).getDescription());
            }
            System.out.println("0. 뒤로가기");

            // 메뉴 입력
            select = inputNumber(category.getProductList().size());
            if (select != 0)
                product = category.getProductList().get(select - 1);


            // 입력값 처리
            if (select == 0) {
                System.out.println("뒤로가기");
            } else {
                System.out.printf("선택한 상품: %s | %d원 | %s | 재고: %d개\n\n", product.getName(), product.getPrice(), product.getDescription(), product.getQuantity());
            }

        }
    }

    private int inputNumber(int size) {
        Scanner sc = new Scanner(System.in);
        int select;
        while (true) {
            try {
                select = sc.nextInt();
                if (!(select >= 0 && select <= size)) { // 범위 밖의 숫자 예외 처리
                    System.out.println("목록 범위 내의 숫자를 입력해주세요.");
                    continue;
                }
                return select;
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}
