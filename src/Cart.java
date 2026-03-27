import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cart;
    private int totalPrice;

    Cart() {
        cart = new ArrayList<>();
        totalPrice = 0;
    }

    public void addCart(Product product) {
        IOHandler.printAddCart(product);
        int input = IOHandler.inputNumber(2);
        
        // 1.예  2. 취소
        if (input == 1) {
            if (cart.isEmpty()) {
                cart.add(new CartItem(product));
            } else {
                boolean isExist = false; // 기존에 장바구니에 담겨 있는 제품인지 확인
                for (CartItem item : cart) {
                    if (item.getItemName().equals(product.getName())) { // 장바구니에 담겨 있는 상품이면 찾아서 개수만 늘리기
                        isExist = true;
                        if (product.getQuantity() - item.getItemAmount() < 1){ // 재고 확인
                            System.out.println("재고가 부족합니다.");
                            return;
                        }
                        else
                            item.changeItemAmount(1);
                    }
                }

                // 장바구니에 담겨 있지 않은 제품이면 담기
                if (!isExist)
                    cart.add(new CartItem(product));
            }

            // 장바구니 최종 추가
            this.totalPrice += product.getPrice();
            System.out.println(product.getName() + "이(가) 장바구니에 추가되었습니다.");

        }

    }

    public List<CartItem> getCart() {
        return cart;
    }


    public void showCart() {
        IOHandler.printOrder(cart, totalPrice);
        int input = IOHandler.inputNumber(2);
        // 1. 주문 확정  2. 메인으로 돌아가기
        if (input == 1) {
            IOHandler.printOrderComplete(cart, totalPrice);
            for (CartItem product : cart) { // 재고 관리
                product.getItem().setQuantity(product.getItem().getQuantity() - product.getItemAmount());
            }
            this.deleteCart(); // 장바구니 비우기
        }
        System.out.println();
    }

    public void deleteCart() {
        while (!cart.isEmpty())
            cart.removeLast();
        this.totalPrice = 0;
    }
}
