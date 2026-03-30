import java.util.ArrayList;
import java.util.List;

public class Category { //
    private String category;
    private List<Product> productList;

    Category(String category) {
        this.category = category;
        productList = new ArrayList<>();
    }

    public String getCategoryName() {
        return category;
    }

    public void setCategoryName(String category) {
        this.category = category;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(String name, int price, String description, int quantity) {
        productList.add(new Product(name, price, description, quantity));
    }

    public void addProduct(Product product) {
        productList.add(product);
    }


}
