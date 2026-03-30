import java.util.List;
import java.util.Scanner;

public class IOHandler {
    public static final Scanner sc = new Scanner(System.in);

    // 0 ~ size 범위의 값 입력
    public static int inputNumber(int size) {
        int select;
        while (true) {
            try {
                select = sc.nextInt();
                if (select >= 0 && select <= size) {
                    sc.nextLine();
                    return select;
                }
                // 범위 밖의 숫자 예외 처리
                System.out.println("목록 범위 내의 숫자를 입력해주세요.");
            } catch (Exception e) {
                System.out.println("정수를 입력해주세요.");
                sc.nextLine(); // 버퍼 비우기
            }
        }
    }

    // 로그인 성공시 true 실패시 false
    public static boolean inputPassword(String password) {
        System.out.println("관리자 비밀번호를 입력해주세요:");
        int chance = 3;
        while (chance > 0) {
            String inputPassword = sc.next();
            if (inputPassword.equals(password))
                return true;
            else {
                System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수: " + --chance + "회");
            }
        }
        return false;
    }

    public static Product inputAddProduct(List<Product> productList) {
        System.out.println("[ 전자제품 카테고리에 상품 추가 ]");
        String name;
        while (true) {
            boolean isExist = false;
            System.out.print("상품명을 입력해주세요: ");
            name = sc.nextLine().trim();
            for (Product product : productList) {
                if (product.getName().trim().equals(name)) {
                    isExist = true;
                    System.out.println("해당 상품은 이미 존재합니다!");
                }
            }
            if (!isExist)
                break;
        }

        int price;
        while (true) {
            try {
                System.out.print("가격을 입력해주세요: ");
                price = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("숫자를 입력해주세요");
                sc.nextLine();
            }
        }
        System.out.print("상품 설명을 입력해주세요: ");
        String description = sc.next();
        int quantity;
        while (true) {
            try {
                System.out.print("재고 수량을 입력해주세요: ");
                quantity = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("숫자를 입력해주세요");
                sc.nextLine();
            }
        }
        System.out.printf("\n%s | %,d원 | %s | 재고: %d개\n", name, price, description, quantity);
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인\t\t2. 취소");
        return new Product(name, price, description, quantity);
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

    public static void printAddProductMenu(List<Category> categories) {
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.println("0. 뒤로가기");
    }

    public static void printModifyProduct() {

    }

    public static void printCategoryProduct(Category category) {
        System.out.println("[ " + category.getCategoryName() + " 카테고리 ]");
        for (int i = 0; i < category.getProductList().size(); i++) {
            System.out.println((i + 1) + ". " + category.getProductList().get(i).getName() + "\t" + "| " + String.format("%,10d", category.getProductList().get(i).getPrice()) + "원 | " + category.getProductList().get(i).getDescription());
        }
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

    public static void printAddCart(Product product) {
        System.out.printf("\"%s | %,d원 | %s\"\n", product.getName(), product.getPrice(), product.getDescription());
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인\t\t2. 취소");
    }
}
