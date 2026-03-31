public class Product {
    private String productName, description;
    private int price, quantity;

    Product(String name, int price, String description, int quantity) {
        this.productName = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return productName;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
