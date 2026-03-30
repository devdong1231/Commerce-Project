import java.util.List;

public class Admin {
    private final String password;
    private List<Category> categories;

    Admin(String password, List<Category> categories) {
        this.password = password;
        this.categories = categories;
    }

    public void adminMenu() {
        while(true){
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
            String password = IOHandler.inputPassword();
            if (password.equals(this.password)) {
                adminMenu();
                return;
            } else
                System.out.println("비밀번호가 틀렸습니다. 남은 시도 횟수 : " + --chance + "회");
        }
        System.out.println("비밀번호를 3회 잘못 입력해서 메인 메뉴로 돌아갑니다.");
    }

    public void createProduct() {
        IOHandler.printCreateProductMenu(categories);

        int select = IOHandler.inputMenu(0, categories.size() + 1);
        if (select != 0) {
            Product product = IOHandler.inputCreateProduct(categories.get(select - 1).getProductList());
            int newSelect = IOHandler.inputMenu(1, 2);
            if (newSelect == 1) {
                System.out.println("상품이 성공적으로 추가되었습니다!");
                categories.get(select - 1).addProduct(product);
            }
        }
    }

    public void updateProduct() {
        Product product;
        while (true) {
            product = null;
            System.out.print("수정할 상품명을 입력해주세요: ");
            String productName = IOHandler.inputUpdateProductName();
            for (Category category : categories) {
                for (int i = 0; i < category.getProductList().size(); i++) {
                    if (category.getProductList().get(i).getName().equals(productName)) {
                        product = category.getProductList().get(i);
                        System.out.println(product.getName() + " " + product.getPrice());
                        break;
                    }
                }
            }
            if (product == null) {
                System.out.println("해당하는 상품이 없습니다!");
            } else break;
        }
        System.out.printf("현재 상품 정보: %s | %,d원 | %s | 재고: %d개\n\n",
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantity());
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

    }

    public void findAllProduct() {

    }
}
