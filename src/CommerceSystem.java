import java.util.ArrayList;
import java.util.List;

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
            int menuChoice = mainMenu(cart);
            if (menuChoice == 0) { // 0번 선택
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }

            if (menuChoice > categories.size()) { // 추가 메뉴 고려
                if (!cart.getCart().isEmpty()) { // 장바구니에 상품이 있을 때
                    if (menuChoice == categories.size() + 1) { // 장바구니 확인
                        cart.showCart();
                    } else if (menuChoice == categories.size() + 2) { // 주문 취소
                        cart.deleteCart();
                    } else if (menuChoice == categories.size() + 3) { // 관리자 모드
                        admin.validPassword();
                    }
                } else { // 장바구니에 상품이 없을 때
                    admin.validPassword();
                }
            }

            else // 카테고리 선택
                subMenu(menuChoice, cart);
        }
    }

    private int mainMenu(Cart cart) {
        // 카테고리 출력, 장바구니에 물건이 담겨있을 시 추가 메뉴 출력
        IOHandler.printMainMenu(categories, !cart.getCart().isEmpty());
        int menuSize = categories.size() + 1; // adminMenu 고려
        if (!cart.getCart().isEmpty()) // 장바구니에 물건이 존재할 시 추가 메뉴 고려
            menuSize += 2;

        return IOHandler.inputMenu(0, menuSize);
    }

    private void subMenu(int mainMenuChoice, Cart cart) {
        Category category = categories.get(mainMenuChoice - 1);
        List<Product> productList;
        IOHandler.printCategoryFilter(category);
        int productChoice;
        int filterSelect = IOHandler.inputMenu(0, 3);
        if (filterSelect == 1) {
            String message = "[ " + category.getCategoryName() + " 카테고리 ]";
            IOHandler.printProductList(category.getProductList(), message);
            productList = category.getProductList();
        } else if (filterSelect == 2) {
            String message = "[ 100만원 이하 상품 목록 ]";
            productList = category.getProductList().stream()
                    .filter(product -> product.getPrice() <= 1000000)
                    .toList();
            IOHandler.printProductList(productList, message);


        } else if (filterSelect == 3) {
            String message = "[ 100만원 초과 상품 목록 ]";
            productList = category.getProductList().stream()
                    .filter(product -> product.getPrice() > 1000000)
                    .toList();
            IOHandler.printProductList(productList, message);
        } else {
            return;
        }

        //메뉴 입력
        productChoice = IOHandler.inputMenu(0, productList.size());
        if (productChoice != 0)
            cart.addCart(productList.get(productChoice - 1));

    }


    public void addCategory(Category category) {
        categories.add(category);
    }

}
