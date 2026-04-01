import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class IOHandler {
    public static final Scanner sc = new Scanner(System.in);

    // 0 ~ size 범위의 값 입력
    public static int inputMenu(int min, int max) {
        int menuSelect;
        while (true) {
            try {
                System.out.print("메뉴 선택: ");
                menuSelect = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기
                if (menuSelect >= min && menuSelect <= max)
                    return menuSelect;
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

    public static int inputUpdatePrice(Product product) {
        System.out.printf("현재 가격: %,d원\n", product.getPrice());
        int newPrice = IOHandler.inputNumber("새로운 가격을 입력해주세요: ");
        System.out.printf("%s의 가격이 %,d원 -> %,d원으로 수정되었습니다.\n", product.getName(), product.getPrice(), newPrice);
        return newPrice;
    }

    public static String inputUpdateDescription(Product product) {
        System.out.printf("현재 설명: %s\n", product.getDescription());
        String newDescription = inputString("새로운 설명을 입력해주세요: ");
        System.out.printf("%s의 설명이 %s로 수정되었습니다.\n", product.getName(), newDescription);
        return newDescription;
    }

    public static int inputUpdateQuantity(Product product) {
        System.out.printf("현재 재고 수량: %,d개\n", product.getQuantity());
        int newQuantity = inputNumber("새로운 재고 수량을 입력해주세요: ");
        System.out.printf("%s의 재고 수량이 %,d개 -> %,d개로 수정되었습니다.\n", product.getName(), product.getQuantity(), newQuantity);
        return newQuantity;
    }

    public static void printMainMenu(List<MenuItem> menu, int categoryCount) {
        System.out.println("-------------------------");
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        // 카테고리 출력
        for (int i = 0; i < categoryCount; i++)
            System.out.println((i + 1) + ". " + menu.get(i).getMenuName());
        //추가 메뉴 출력 - 장바구니
        if (menu.size() > categoryCount + 1) {
            System.out.println("\n[ 주문 관리 ]");
            for (int i = categoryCount; i < menu.size(); i++)
                System.out.printf("%d. %s\n", i + 1, menu.get(i).getMenuName());
        }
        // 추가 메뉴 출력 - 관리자 모드
        else
            System.out.println(menu.size() + ". " + menu.get(menu.size() - 1).getMenuName());
        System.out.println("0. 종료\t| 프로그램 종료");
        System.out.println("-------------------------");
    }

    public static void printAdminMenu(List<MenuItem> menu) {
        System.out.println("-------------------------");
        System.out.println("[ 관리자 모드 ]");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getMenuName());
        }
        System.out.println("0. 메인으로 돌아가기");
        System.out.println("-------------------------");
    }

    public static void printUpdateProductMenu(List<MenuItem> menu) {
        System.out.println("-------------------------");
        System.out.println("수정할 항목을 선택해주세요:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, menu.get(i).getMenuName());
        }
        System.out.println("-------------------------");
    }

    public static void printCategoryList(List<Category> categories, String message) {
        if (!message.isEmpty())
            System.out.println(message);
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("0. 뒤로가기");
    }

    public static void printProductList(List<Product> category, String message) {
        System.out.println("-------------------------");
        if (!message.isEmpty())
            System.out.println(message);
        IntStream.range(0, category.size()).forEach(i -> {
            System.out.printf("%d. %s\t%,10d원 | %20s | 재고: %d개\n",
                    i + 1,
                    category.get(i).getName(),
                    category.get(i).getPrice(),
                    category.get(i).getDescription(),
                    category.get(i).getQuantity()
            );
        });
        System.out.println("0. 뒤로가기");
        System.out.println("-------------------------");
    }

    public static void printCategoryFilter(List<MenuItem> menu, String categoryName) {
        System.out.println("-------------------------");
        System.out.printf("[ %s 카테고리 ]\n", categoryName);
        for (int i = 0; i < menu.size(); i++)
            System.out.println((i + 1) + ". " + menu.get(i).getMenuName());
        System.out.println("0. 뒤로가기");
        System.out.println("-------------------------");
    }

    public static void printOrder(List<CartItem> cart, String message) {
        if (!message.isEmpty())
            System.out.println(message);
        System.out.println("[ 장바구니 내역 ]");
        cart.stream()
                .forEach(product -> {
                    int index = cart.indexOf(product) + 1;
                    System.out.printf("%d. %s | %,d원 | %s | 수량: %d개\n",
                            index,
                            product.getItemName(),
                            product.getItemPrice(),
                            product.getItemDescription(),
                            product.getItemAmount()
                    );
                });
    }

    public static void printOrderConfirm(int totalPrice) {
        System.out.println("\n[ 총 주문 금액 ]\n" + String.format("%,d", totalPrice) + "원");
        System.out.println("\n1. 주문 확정 \t\t 2. 메인으로 돌아가기");
    }

    public static void printOrderComplete(List<CartItem> cart, int totalPrice, CustomerGrade grade) {
        System.out.println("주문이 완료되었습니다!");
        System.out.println("할인 전 금액: " + String.format("%,d", totalPrice) + "원");
        System.out.printf("%s 등급 할인(%d%%): %,d원\n", grade, (int) (grade.getGrade() * 100), grade.getDiscount(totalPrice));
        System.out.println("최종 결제 금액: " + String.format("%,d", totalPrice - grade.getDiscount(totalPrice)) + "원");
        for (CartItem product : cart) {
            System.out.printf("%s 재고가 %d개 -> %d개로 업데이트 되었습니다.\n",
                    product.getItemName(),
                    product.getItem().getQuantity() + product.getItemAmount(),
                    product.getItem().getQuantity()
            );
        }
    }

    public static void printDeleteCart(List<MenuItem> menu) {
        for (int i = 0; i < menu.size(); i++)
            System.out.printf("%d. %s", i + 1, menu.get(i).getMenuName());
        System.out.println("0. 뒤로 가기");
    }

    public static void printCurrentProduct(Product product) {
        System.out.printf("\"%s | %,d원 | %s | 재고: %d개\"\n", product.getName(), product.getPrice(), product.getDescription(), product.getQuantity());
    }

    public static void printConfirmMessage(String message) {
        System.out.println("-------------------------");
        System.out.println(message);
        System.out.println("1. 확인\t\t2. 취소");
        System.out.println("-------------------------");
    }

    public static void printGradeMenu(List<MenuItem> menu) {
        System.out.println("고객 등급을 입력해주세요.");
        int cnt = 1;
        for (CustomerGrade grade : CustomerGrade.values()) {
            System.out.printf("%d. %-9s: %2d%% 할인\n", cnt++, grade, (int) (grade.getGrade() * 100));
        }
    }
}