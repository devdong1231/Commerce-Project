import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class CommerceSystem {
    private List<Category> categories;
    private Admin admin;
    private Cart cart;

    CommerceSystem(String password) {
        categories = new ArrayList<>();
        cart = new Cart();
        admin = new Admin(password, categories, cart);
    }

    public void start() {
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        // 메뉴 등록
        List<MenuItem> menu = new ArrayList<>();

        for (Category category : categories) {
            menu.add(new MenuItem(category.getCategoryName(), () -> {
                subMenu(category);
            }));
        }
        if (!cart.getCart().isEmpty()) {
            menu.add(new MenuItem("장바구니 확인\t\t 장바구니를 확인 후 주문합니다.", () -> {
                cart.orderCart();
            }));
            menu.add(new MenuItem("주문 취소\t\t\t 진행 중인 주문을 취소합니다.", () -> {
                cart.deleteCart();
            }));
        }
        menu.add(new MenuItem("관리자 모드", () -> {
            admin.validPassword();
        }));
        // 메뉴 출력
        IOHandler.printMainMenu(menu, categories.size());
        // 메뉴 입력
        int menuChoice = IOHandler.inputMenu(0, menu.size());
        // 메뉴 실행
        if (menuChoice == 0) {
            System.out.println("커머스 플랫폼을 종료합니다.");
            exit(1);
        }
        menu.get(menuChoice - 1).execute();
    }

    private void subMenu(Category category) {
        List<MenuItem> menu = new ArrayList<>();
        // 메뉴 등록
        menu.add(new MenuItem("전체 상품 보기", () -> {
            String message = "[ " + category.getCategoryName() + " 카테고리 ]";
            List<Product> productList = category.getProductList();
            productMenu(message, productList);
        }));
        menu.add(new MenuItem("가격대별 필터링 (100만원 이하)", () -> {
            String message = "[ 100만원 이하 상품 목록 ]";
            List<Product> productList = category.getProductList().stream()
                    .filter(product -> product.getPrice() <= 1000000)
                    .toList();
            productMenu(message, productList);
        }));
        menu.add(new MenuItem("가격대별 필터링 (100만원 초과)", () -> {
            String message = "[ 100만원 초과 상품 목록 ]";
            List<Product> productList = category.getProductList().stream()
                    .filter(product -> product.getPrice() > 1000000)
                    .toList();
            productMenu(message, productList);
        }));
        // 메뉴 출력
        IOHandler.printCategoryFilter(menu, category.getCategoryName());
        // 메뉴 입력
        int menuSelect = IOHandler.inputMenu(0, menu.size());
        // 메뉴 실행
        if (menuSelect != 0)
            menu.get(menuSelect - 1).execute();
    }

    private void productMenu(String message, List<Product> productList) {
        // 상품 출력
        IOHandler.printProductList(productList, message);
        // 상품 입력
        int productChoice = IOHandler.inputMenu(0, productList.size());
        // 상품 추가
        if (productChoice != 0)
            cart.addCart(productList.get(productChoice - 1));
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}