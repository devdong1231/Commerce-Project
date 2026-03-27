import java.util.ArrayList;
import java.util.List;

public class CommerceSystem {
    private List<Category> categories;

    CommerceSystem() {
        categories = new ArrayList<>();
    }

    public void start() {
        Cart cart = new Cart();
        int select = 0;
        Category category = null;
        Product product = null;
        while (true) {
            // 카테고리 출력
            IOHandler.printMainMenu(categories, !cart.getCart().isEmpty());
            int size = categories.size();
            if (!cart.getCart().isEmpty()) {
                size += 2;
            }

            select = IOHandler.inputNumber(size);

            // 0번 선택
            if (select == 0) {
                System.out.println("커머스 플랫폼을 종료합니다.");
                return;
            }


            // 장바구니 확인
            else if (select == categories.size() + 1) cart.showCart();

            // 장바구니 비우기
            else if (select == categories.size() + 2) cart.deleteCart();

            // 세부 카테고리로 이동
            else {
                category = categories.get(select - 1);
                IOHandler.printCategoryProduct(category);

                // 메뉴 입력
                select = IOHandler.inputNumber(category.getProductList().size());
                if (select != 0) product = category.getProductList().get(select - 1);

                // 입력값 처리
                if (select == 0) {
                    System.out.println("뒤로가기");
                } else {
                    cart.addCart(category.getProductList().get(select - 1));
                }
            }
        }
    }


    public void addCategory(Category category) {
        categories.add(category);
    }

}
