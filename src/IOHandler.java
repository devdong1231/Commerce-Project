import java.util.List;
import java.util.Scanner;

public class IOHandler {
    // 0 ~ size 범위의 값 입력
    public static int inputNumber(int size) {
        Scanner sc = new Scanner(System.in);
        int select;
        while (true) {
            try {
                select = sc.nextInt();
                if (select >= 0 && select <= size)
                    return select;
                // 범위 밖의 숫자 예외 처리
                System.out.println("목록 범위 내의 숫자를 입력해주세요.");
            } catch (Exception e) {
                System.out.println("정수를 입력해주세요.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
    }

    public static void printMainMenu(List<Category> categories, boolean cartState) {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++)
            System.out.println((i + 1) + ". " + categories.get(i).getCategory());
        System.out.println("0. 종료\t| 프로그램 종료");
        if (cartState) {
            System.out.println("\n[ 주문 관리 ]");
            System.out.println((categories.size() + 1) + ". 장바구니 확인\t\t 장바구니를 확인 후 주문합니다.");
            System.out.println((categories.size() + 2) + ". 주문 취소\t\t\t 진행 중인 주문을 취소합니다.");
        }
    }

    public static void printCategoryProduct(Category category) {
        System.out.println("[ " + category.getCategory() + " 카테고리 ]");
        for (int i = 0; i < category.getProductList().size(); i++) {
            System.out.println((i + 1) + ". " + category.getProductList().get(i).getName() + "\t" + "| " + String.format("%,10d", category.getProductList().get(i).getPrice()) + "원 | " + category.getProductList().get(i).getDescription());
        }
        System.out.println("0. 뒤로가기");

    }

    public static void printOrder(List<Product> cart, int totalPrice) {
        System.out.println("\n아래와 같이 주문하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");
        for (Product product : cart) {
            System.out.printf("%s | %,d원 | %s | 수량: %d개\n",
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getQuantity());
        }
        System.out.println("\n[ 총 주문 금액 ]\n" + String.format("%,d", totalPrice) + "원");
        System.out.println("\n1. 주문 확정 \t\t 2. 메인으로 돌아가기");
    }

    // TODO - 출력이 아닌 문장 다른 곳으로 옮기기
    public static void printOrderComplete(List<Product> cart, int totalPrice) {
        System.out.println("주문이 완료되었습니다!  총 금액: " + String.format("%,d", totalPrice) + "원");
        for (Product product : cart) {
            System.out.printf("%s 재고가 %d개 -> %d개로 업데이트 되었습니다.\n", product.getName(), product.getQuantity(), product.getQuantity() - 1);
            product.setQuantity(product.getQuantity() - 1);
        }
    }

    public static void printAddCart(Product product) {
        System.out.printf("\"%s | %,d원 | %s\"\n", product.getName(), product.getPrice(), product.getDescription());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인\t\t2. 취소");
    }
}
