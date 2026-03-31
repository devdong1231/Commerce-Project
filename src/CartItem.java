public class CartItem {
    private Product product;
    private int amount;

    CartItem(Product product) {
        this.product = product;
        this.amount = 1;
    }

    public Product getItem() {
        return product;
    }
    
    public String getItemName() {
        return product.getName();
    }

    public String getItemDescription() {
        return product.getDescription();
    }

    public int getItemPrice() {
        return product.getPrice();
    }

    public int getItemAmount() {
        return amount;
    }


    public void changeItemAmount(int amount) {
        this.amount += amount;
    }

}
