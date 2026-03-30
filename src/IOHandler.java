import java.util.List;
import java.util.Scanner;

public class IOHandler {
    public static final Scanner sc = new Scanner(System.in);

    // 0 ~ size 범위의 값 입력
    public static int inputMenu(int min, int max) {
        int menuSelect;
        while (true) {
            try {
                menuSelect = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                if (menuSelect >= min && menuSelect <= max) {
                    return menuSelect;
                }
                // 범위 밖의 숫자 예외 처리
                System.out.println("목록 범위 내의 숫자를 입력해주세요.");
            } catch (Exception e) {
                System.out.println("정수를 입력해주세요.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
    }

    public static String inputString(String message) {
        if (!message.isEmpty())
            System.out.print(message);
        return sc.nextLine().trim();
    }

    public static int inputNumber(String message) {
        int number;
        while (true) {
            try {
                if (!message.isEmpty()) // 입력 직전 메시지
                    System.out.print(message);
                number = sc.nextInt();
                if (number < 1) {
                    throw new Exception();
                } else {
                    sc.nextLine();
                    return number;
                }
            } catch (Exception e) {
                System.out.println("1 이상의 정수를 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    public static int inputUpdateProductPrice(Product product) {
        System.out.printf("현재 가격: %,d원\n", product.getPrice());
        int newPrice = IOHandler.inputNumber("새로운 가격을 입력해주세요: ");
        System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n", product.getName(), product.getPrice(), newPrice);
        return newPrice;
    }

    public static String inputUpdateProductDescription(Product product) {
        System.out.printf("현재 설명: %s\n", product.getPrice());
        String newDescription = inputString("새로운 설명을 입력해주세요: ");
        System.out.printf("%s의 설명이 %s로 수정되었습니다.\n", product.getName(), product.getDescription());
        return newDescription;

    }

    public static int inputUpdateProductQuantity(Product product) {
        System.out.printf("현재 재고 수량: %,d원\n", product.getQuantity());
        int newQuantity = inputNumber("새로운 재고 수량을 입력해주세요: ");
        System.out.printf("%s의 재고 수량이 %,d개 -> %,d개로 수정되었습니다.\n", product.getName(), product.getQuantity(), newQuantity);
        return newQuantity;
    }

    public static void printMainMenu(List<Category> categories, boolean cartState) {
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for (int i = 0; i < categories.size(); i++)
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        System.out.println("0. 종료\t| 프로그램 종료");
        if (cartState) {
            System.out.println((categories.size() + 3) + ". 관리자 모드");
            System.out.println("\n[ 주문 관리 ]");
            System.out.println((categories.size() + 1) + ". 장바구니 확인\t\t 장바구니를 확인 후 주문합니다.");
            System.out.println((categories.size() + 2) + ". 주문 취소\t\t\t 진행 중인 주문을 취소합니다.");
        } else {
            System.out.println((categories.size() + 1) + ". 관리자 모드");
        }
    }

    public static void printAdminMenu() {
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 수정");
        System.out.println("3. 상품 삭제");
        System.out.println("4. 전체 상품 현황");
        System.out.println("0. 메인으로 돌아가기");
    }

    public static void printCategoryList(List<Category> categories, String message) {
        System.out.println(message);
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("0. 뒤로가기");
    }

    public static void printProductList(List<Product> category, String message) {
        System.out.println(message);
        for (int i = 0; i < category.size(); i++) {
            System.out.printf("%d. %s\t%,10d원 | %s | 재고: %d개\n",
                    i + 1,
                    category.get(i).getName(),
                    category.get(i).getPrice(),
                    category.get(i).getDescription(),
                    category.get(i).getQuantity()
            );
        }
        System.out.println("0. 뒤로가기");
    }

    public static void printCategoryFilter(Category category) {
        System.out.printf("[ %s 카테고리 ]\n", category.getCategoryName());
        System.out.println("1. 전체 상품 보기");
        System.out.println("2. 가격대별 필터링 (100만원 이하)");
        System.out.println("3. 가격대별 필터링 (100만원 초과)");
        System.out.println("0. 뒤로가기");
    }

    public static void printOrder(List<CartItem> cart, int totalPrice) {
        System.out.println("\n아래와 같이 주문하시겠습니까?\n");
        System.out.println("[ 장바구니 내역 ]");
        for (CartItem product : cart) {
            System.out.printf("%s | %,d원 | %s | 수량: %d개\n",
                    product.getItemName(),
                    product.getItemPrice(),
                    product.getItemDescription(),
                    product.getItemAmount());
        }
        System.out.println("\n[ 총 주문 금액 ]\n" + String.format("%,d", totalPrice) + "원");
        System.out.println("\n1. 주문 확정 \t\t 2. 메인으로 돌아가기");
    }

    public static void printOrderComplete(List<CartItem> cart, int totalPrice) {
        System.out.println("주문이 완료되었습니다!  총 금액: " + String.format("%,d", totalPrice) + "원");
        for (CartItem product : cart) {
            System.out.printf("%s 재고가 %d개 -> %d개로 업데이트 되었습니다.\n",
                    product.getItemName(),
                    product.getItem().getQuantity(),
                    product.getItem().getQuantity() - product.getItemAmount()
            );
        }
    }


    public static void printCurrentProduct(Product product) {
        System.out.printf("\"%s | %,d원 | %s\"\n", product.getName(), product.getPrice(), product.getDescription());
    }

    public static void printConfirmMessage(String message) {
        System.out.println(message);
        System.out.println("1. 확인\t\t2. 취소");
    }

}
