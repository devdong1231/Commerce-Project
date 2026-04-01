import java.util.ArrayList;
import java.util.List;

public class Admin {
    private final String password;
    private final int MAX_LOGIN_ATTEMPTS = 3;
    private List<Category> categories;
    private Cart cart;

    Admin(String password, List<Category> categories, Cart cart) {
        this.password = password;
        this.categories = categories;
        this.cart = cart;
    }

    public void adminMenu() {
        while (true) {
            List<MenuItem> menu = new ArrayList<>();
            // 메뉴 등록
            menu.add(new MenuItem("상품 추가", this::createProduct));
            menu.add(new MenuItem("상품 수정", this::updateProduct));
            menu.add(new MenuItem("상품 삭제", this::deleteProduct));
            menu.add(new MenuItem("전체 상품 현황", this::findAllProduct));
            // 메뉴 출력
            IOHandler.printAdminMenu(menu);
            // 메뉴 입력
            int menuSelect = IOHandler.inputMenu(0, 4);
            // 메뉴 실행
            if (menuSelect == 0)
                return;
            menu.get(menuSelect - 1).execute();
        }
    }

    public void validPassword() {
        int loginAttempts = 0;
        System.out.println("-------------------------");
        System.out.println("관리자 비밀번호를 입력해주세요");
        while (loginAttempts <= MAX_LOGIN_ATTEMPTS) {
            loginAttempts++;
            String password = IOHandler.inputString("비밀번호 입력: ");
            if (password.equals(this.password)) { // 로그인 성공
                adminMenu();
                return;
            } else // 로그인 실패
                System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수 : " + (MAX_LOGIN_ATTEMPTS - loginAttempts) + "회");
        }
        // MAX_LOGIN_ATTEMPTS 만큼 로그인 시도 후 실패
        System.out.println("비밀번호를 3회 잘못 입력해서 메인 메뉴로 돌아갑니다.");
    }

    private void createProduct() {
        // 카테고리 출력
        IOHandler.printCategoryList(categories, "어느 카테고리에 상품을 추가하시겠습니까?");

        // 카테고리 입력
        int categorySelect = IOHandler.inputMenu(0, categories.size() + 1);

        // 뒤로 가기
        if (categorySelect == 0) return;

        System.out.printf("[ %s 카테고리에 상품 추가 ]\n", categories.get(categorySelect - 1).getCategoryName());
        String productName = inputNewProductName("상품명을 입력해주세요: ");

        //상품 정보 입력
        int price = IOHandler.inputNumber("가격을 입력해주세요: ");
        String description = IOHandler.inputString("상품 설명을 입력해주세요: ");
        int quantity = IOHandler.inputNumber("재고 수량을 입력해주세요: ");
        Product product = new Product(productName, price, description, quantity);

        // 현재 상품 정보 & 컨펌 메세지
        IOHandler.printCurrentProduct(product);
        IOHandler.printConfirmMessage("위 정보로 상품을 추가하시겠습니까?");

        // 1. 예, 2. 아니오
        int newSelect = IOHandler.inputMenu(1, 2);
        if (newSelect == 1) {
            System.out.println("상품이 성공적으로 추가되었습니다!");
            categories.get(categorySelect - 1).addProduct(product);
        }

    }

    private void updateProduct() {
        System.out.println("-------------------------");
        Product product = inputExistingProduct("수정할 상품명을 입력해주세요: ");
        // 메뉴 등록
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("가격", () -> {
            product.setPrice(IOHandler.inputUpdatePrice(product));
        }));
        menu.add(new MenuItem("설명", () -> {
            product.setDescription(IOHandler.inputUpdateDescription(product));
        }));
        menu.add(new MenuItem("재고 수량", () -> {
            product.setQuantity(IOHandler.inputUpdateQuantity(product));
        }));
        // 메뉴 출력
        IOHandler.printUpdateProductMenu(menu);
        // 메뉴 입력
        int menuSelect = IOHandler.inputMenu(1, 3);
        // 메뉴 실행
        menu.get(menuSelect - 1).execute();
    }

    private void deleteProduct() {
        Product product = inputExistingProduct("삭제할 상품명을 입력해주세요: ");
        IOHandler.printConfirmMessage("위 상품을 정말 삭제하시겠습니까?");
        int confirmSelect = IOHandler.inputMenu(1, 2);

        if (confirmSelect == 2) // 취소
            return;
        
        for (Category category : categories) { // 전체 상품 목록 내에서 삭제
            for (int i = 0; i < category.getProductList().size(); i++) {
                if (category.getProductList().get(i) == product) {
                    category.getProductList().remove(product);
                }
            }
        }
        // 장바구니에서 삭제
        cart.deleteCartProduct(product);
        System.out.println("상품을 삭제하였습니다.");
    }

    private void findAllProduct() {
        IOHandler.printCategoryList(categories, "");
        int categorySelect = IOHandler.inputMenu(0, categories.size());
        if (categorySelect != 0) {
            IOHandler.printProductList(categories.get(categorySelect - 1).getProductList(), "");
        }
    }

    private boolean isProductExists(String productName) {
        return findProductByName(productName) != null;
    }

    private Product findProductByName(String productName) { // 이름으로 상품 찾기
        for (Category category : categories) { // 카테고리 순회
            for (Product product : category.getProductList()) { // 상품 목록 순회
                if (product.getName().equals(productName)) {
                    return product;
                }
            }
        }
        return null;
    }

    // 입력 받은 상품과 일치하는 상품의 이름이 있는지 확인 후 입력값 반환
    private String inputNewProductName(String message) {
        while (true) {
            String productName = IOHandler.inputString(message);
            if (!isProductExists(productName))
                return productName;
            System.out.println("이미 존재하는 상품입니다.");
        }
    }

    // 입력 받은 상품의 이름과 일치하는 상품의 이름이 있을 때 상품 반환
    private Product inputExistingProduct(String message) {
        while (true) {
            String productName = IOHandler.inputString(message);
            Product product = findProductByName(productName);
            if (product != null) {
                IOHandler.printCurrentProduct(product);
                return product;
            }
            System.out.println("해당 상품은 존재하지 않습니다.");
        }
    }
}