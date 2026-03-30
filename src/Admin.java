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
                default:
                    break;
            }
        }

    }

    public void validPassword() {
        int chance = 3;
        System.out.println("관리자 비밀번호를 입력해주세요");
        while (chance > 0) {
            String password = IOHandler.inputString("");
            if (password.equals(this.password)) {
                adminMenu();
                return;
            } else
                System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수 : " + --chance + "회");
        }
        System.out.println("비밀번호를 3회 잘못 입력해서 메인 메뉴로 돌아갑니다.");
    }

    public void createProduct() {
        IOHandler.printCategoryList(categories, "어느 카테고리에 상품을 추가하시겠습니까?");

        int categorySelect = IOHandler.inputMenu(0, categories.size() + 1);
        if (categorySelect != 0) {
            System.out.printf("[ %s 카테고리에 상품 추가 ]\n", categories.get(categorySelect - 1).getCategoryName());
            String productName;
            while (true) {
                productName = IOHandler.inputString("상품명을 입력해주세요: ");
                if (validCheckItem(productName)) {
                    System.out.println("이미 존재하는 상품입니다.");
                } else break;
            }
            int price = IOHandler.inputNumber("가격을 입력해주세요: ");
            String description = IOHandler.inputString("상품 설명을 입력해주세요: ");
            int quantity = IOHandler.inputNumber("재고 수량을 입력해주세요: ");
            Product product = new Product(productName, price, description, quantity);

            IOHandler.printCurrentProduct(product);
            IOHandler.printConfirmMessage("위 정보로 상품을 추가하시겠습니까?");

            int newSelect = IOHandler.inputMenu(1, 2);
            if (newSelect == 1) {
                System.out.println("상품이 성공적으로 추가되었습니다!");
                categories.get(categorySelect - 1).addProduct(product);
            }
        }
    }

    public void updateProduct() {
        Product product = findProduct("수정할 상품명을 입력해주세요: ", "해당 상품은 존재하지 않습니다.");

        System.out.println("수정할 항목을 선택해주세요:\n");
        System.out.println("1.가격\n2.설명\n3.재고수량");
        int menuSelect = IOHandler.inputMenu(1, 3);
        switch (menuSelect) {
            case 1:
                int newPrice = IOHandler.inputUpdateProductPrice(product);
                product.setPrice(newPrice);
                break;
            case 2:
                String newDescription = IOHandler.inputUpdateProductDescription(product);
                product.setDescription(newDescription);
                break;
            case 3:
                int newQuantity = IOHandler.inputUpdateProductQuantity(product);
                product.setQuantity(newQuantity);
                break;
        }
    }

    public void deleteProduct() {
        Product product = findProduct("삭제할 상품명을 입력해주세요: ", "해당 상품은 존재하지 않습니다.");
        IOHandler.printConfirmMessage("위 상품을 정말 삭제하시겠습니까?");

        int confirmSelect = IOHandler.inputMenu(1, 2);
        if (confirmSelect == 1) {
            // 전체 상품 목록 내에서 삭제
            for (Category category : categories) {
                for (int i = 0; i < category.getProductList().size(); i++) {
                    if (category.getProductList().get(i) == product) {
                        category.getProductList().remove(product);
                    }
                }
            }
            // 장바구니에서 삭제
            for (int i = 0; i < cart.getCart().size(); i++) {
                if (cart.getCart().get(i).getItemName().equals(product.getName())) {
                    cart.getCart().remove(i);
                    break;
                }
            }

            for (CartItem item : cart.getCart()) {
                if (item.getItemName().equals(product.getName())) {
                    cart.getCart().remove(item);
                    break;
                }
            }
            System.out.println("상품을 삭제하였습니다.");
        }

    }

    public void findAllProduct() {

    }

    // 동일한 이름이 있는지 검증하는 함수 true : 동일 이름 존재
    public boolean validCheckItem(String name) {
        for (Category category : categories) {
            for (int i = 0; i < category.getProductList().size(); i++) {
                if (category.getProductList().get(i).getName().equals(name))
                    return true;
            }
        }
        return false;
    }

    public Product findProduct(String message, String errormessage) {
        String name;
        Product product = null;
        while (true) {
            name = IOHandler.inputString(message);
            if (!validCheckItem(name)) {
                System.out.println(errormessage);
            } else
                break;
        }
        for (Category category : categories) {
            for (int i = 0; i < category.getProductList().size(); i++) {
                if (category.getProductList().get(i).getName().equals(name))
                    product = category.getProductList().get(i);
            }
        }
        IOHandler.printCurrentProduct(product);

        return product;
    }
}
