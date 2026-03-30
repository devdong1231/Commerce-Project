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
            // 0번 선택
            if (menuChoice == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }

            // 추가 메뉴 고려
            if (menuChoice > categories.size()) {
                if (!cart.getCart().isEmpty()) {
                    if (menuChoice == categories.size() + 1) { // 장바구니 확인
                        cart.showCart();
                    } else if (menuChoice == categories.size() + 2) { // 주문 취소
                        cart.deleteCart();
                    } else if (menuChoice == categories.size() + 3) { // 관리자 모드
                        admin.validPassword();
                    }
                } else {
                    admin.validPassword();
                }
            } else {
                // 세부 카테고리로 이동
                subMenu(menuChoice, cart);
            }


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
        IOHandler.printCategoryProduct(category);

        //메뉴 입력
        int productChoice = IOHandler.inputMenu(0, category.getProductList().size());

        if (productChoice == 0)
            System.out.println("뒤로가기");
        else
            cart.addCart(category.getProductList().get(productChoice - 1));
    }


    public List<Category> getCategories() {
        return categories;
    }


    public void addCategory(Category category) {
        categories.add(category);
    }

}
