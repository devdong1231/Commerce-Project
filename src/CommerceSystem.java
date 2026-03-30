import java.util.ArrayList;
import java.util.List;

public class CommerceSystem {
    private List<Category> categories;
    private final String password = "asdf";

    CommerceSystem() {
        categories = new ArrayList<>();
    }

    public void start() {
        Cart cart = new Cart();
        while (true) {
            Category category;
            boolean isAdmin = false;
            int select;
            // 카테고리 출력, 장바구니에 물건이 담겨있을 시 추가 메뉴 출력
            IOHandler.printMainMenu(categories, !cart.getCart().isEmpty());
            int size = categories.size() + 1; // admin 메뉴 고려

            // 장바구니에 물건이 있을 시 추가 메뉴를 고려
            if (!cart.getCart().isEmpty()) {
                size += 2;
            }

            select = IOHandler.inputNumber(size);

            // 0번 선택
            if (select == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }

            // 장바구니에 물건이 있을 시 추가 메뉴 고려
            if (!cart.getCart().isEmpty()) {
                if (select == categories.size() + 1) { // 장바구니 확인
                    cart.showCart();
                } else if (select == categories.size() + 2) { // 주문 취소
                    cart.deleteCart();
                } else if (select == categories.size() + 3) { // 관리자 모드
                    isAdmin = IOHandler.inputPassword(password);
                }
            } else {
                if (select == categories.size() + 1) // 관리자 모드
                    isAdmin = IOHandler.inputPassword(password);
            }

            if (isAdmin) {
                while (true) {
                    IOHandler.printAdminMenu();
                    int adminSelect = IOHandler.inputNumber(4);
                    if (adminSelect == 0)
                        break;
                    else if (adminSelect == 1)
                        Admin.addProduct(categories);
                    else if (adminSelect == 2)
                        IOHandler.printModifyProduct();
                }

            } else {
                // 세부 카테고리로 이동
                if (select <= categories.size()) {
                    category = categories.get(select - 1);
                    IOHandler.printCategoryProduct(category);

                    // 메뉴 입력
                    select = IOHandler.inputNumber(category.getProductList().size());

                    // 입력값 처리
                    if (select == 0) {
                        System.out.println("뒤로가기");
                    } else {
                        cart.addCart(category.getProductList().get(select - 1));
                    }
                }
            }


        }
    }

    public List<Category> getCategories() {
        return categories;
    }


    public void addCategory(Category category) {
        categories.add(category);
    }

}
