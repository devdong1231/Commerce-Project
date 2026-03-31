import java.util.List;

public class Admin {
    private final String password;
    private List<Category> categories;
    private Cart cart;

    Admin(String password, List<Category> categories, Cart cart) {
        this.password = password;
        this.categories = categories;
        this.cart = cart;
    }

    public void adminMenu() {
        while (true) {
            IOHandler.printAdminMenu();
            int menuSelect = IOHandler.inputMenu(0, 4);
            switch (menuSelect) {
                case 0:
                    return;
                case 1:
                    createProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    findAllProduct();
                    break;
            }
        }

    }

    public void validPassword() {
        int chance = 3;
        System.out.println("관리자 비밀번호를 입력해주세요");
        while (chance > 0) {
            String password = IOHandler.inputString("비밀번호 입력: ");
            if (password.equals(this.password)) { // 로그인 성공
                adminMenu();
                return;
            } else // 로그인 실패
                System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수 : " + --chance + "회");
        }
        // 로그인 3회 시도 후 실패
        System.out.println("비밀번호를 3회 잘못 입력해서 메인 메뉴로 돌아갑니다.");
    }

    private void createProduct() {
        // 카테고리 출력
        IOHandler.printCategoryList(categories, "어느 카테고리에 상품을 추가하시겠습니까?");

        // 카테고리 입력
        int categorySelect = IOHandler.inputMenu(0, categories.size() + 1);

        if (categorySelect != 0) {
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
    }

    private void updateProduct() {
        Product product = inputExistingProduct("수정할 상품명을 입력해주세요: ");

        System.out.println("수정할 항목을 선택해주세요:\n");
        System.out.println("1.가격\n2.설명\n3.재고수량");
        int menuSelect = IOHandler.inputMenu(1, 3);
        switch (menuSelect) {
            case 1:
                int newPrice = IOHandler.inputUpdatePrice(product);
                product.setPrice(newPrice);
                break;
            case 2:
                String newDescription = IOHandler.inputUpdateDescription(product);
                product.setDescription(newDescription);
                break;
            case 3:
                int newQuantity = IOHandler.inputUpdateQuantity(product);
                product.setQuantity(newQuantity);
                break;
        }
    }

    private void deleteProduct() {
        Product product = inputExistingProduct("삭제할 상품명을 입력해주세요: ");
        IOHandler.printConfirmMessage("위 상품을 정말 삭제하시겠습니까?");

        int confirmSelect = IOHandler.inputMenu(1, 2);
        if (confirmSelect == 1) {
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

    }

    public void findAllProduct() {
        IOHandler.printCategoryList(categories, "");
        int categorySelect = IOHandler.inputMenu(0, categories.size());
        if (categorySelect != 0) {
            IOHandler.printProductList(categories.get(categorySelect - 1).getProductList(), "");
            System.out.println("-------------------------");
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

    private String inputNewProductName(String message) {
        while (true) {
            String productName = IOHandler.inputString(message);
            if (!isProductExists(productName))
                return productName;
            System.out.println("이미 존재하는 상품입니다.");
        }
    }

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
