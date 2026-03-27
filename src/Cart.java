import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> cart;
    private int totalPrice;

    Cart() {
        cart = new ArrayList<>();
        totalPrice = 0;
    }

    public void addCart(Product product) {
        IOHandler.printAddCart(product);
        int input = IOHandler.inputNumber(2);
        if (input == 1) {
            cart.add(product);
            this.totalPrice += product.getPrice();
            System.out.println(product.getName() + "이(가) 장바구니에 추가되었습니다.");
            
        }

    }

    public List<Product> getCart() {
        return cart;
    }

    public void showCart() {
        IOHandler.printOrder(cart, totalPrice);
        int input = IOHandler.inputNumber(2);
        if (input == 1) {
            IOHandler.printOrderComplete(cart, totalPrice);
            this.deleteCart();
        }
        System.out.println();
    }

    public void deleteCart() {
        while (!cart.isEmpty())
            cart.removeLast();
        this.totalPrice = 0;
    }
}
