import java.util.List;

public class Admin {
    private String password = "asdf";

    public static void validPassword() {
        int chance = 3;
//        IOHandler.inputPassword()

    }

    public static void addProduct(List<Category> categories) {
        IOHandler.printAddProductMenu(categories);
        int select = IOHandler.inputNumber(categories.size() + 1);
        if (select != 0) {
            Product product = IOHandler.inputAddProduct(categories.get(select - 1).getProductList());
            int newSelect = IOHandler.inputNumber(2);
            if (newSelect == 1) {
                System.out.println("상품이 성공적으로 추가되었습니다!");
                categories.get(select - 1).addProduct(product);
            }
        }
    }
}
