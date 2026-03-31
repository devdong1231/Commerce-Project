import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<CartItem> cart;
    private int totalPrice;

    Cart() {
        cart = new ArrayList<>();
        totalPrice = 0;
    }

    public void addCart(Product product) {
        IOHandler.printCurrentProduct(product);
        IOHandler.printConfirmMessage("위 상품을 장바구니에 추가하시겠습니까?");

        int input = IOHandler.inputMenu(1, 2);

        if (input == 1) { // 예
            if (cart.isEmpty()) {
                cart.add(new CartItem(product));
            } else { // 아니오
                boolean isExist = false; // 기존에 장바구니에 담겨 있는 제품인지 확인
                for (CartItem item : cart) {
                    if (item.getItemName().equals(product.getName())) { // 장바구니에 담겨 있는 상품이면 찾아서 개수만 늘리기
                        isExist = true;
                        if (product.getQuantity() - item.getItemAmount() < 1) { // 재고 부족
                            System.out.println("재고가 부족합니다.");
                            return;
                        } else
                            item.changeItemAmount(1);
                    }
                }
                // 장바구니에 담겨 있지 않은 제품이면 담기
                if (!isExist)
                    cart.add(new CartItem(product));
            }
            // 장바구니 최종 금액 추가
            this.totalPrice += product.getPrice();
            product.setQuantity(product.getQuantity() - 1);
            System.out.println(product.getName() + "이(가) 장바구니에 추가되었습니다.");
        }
    }

    public List<CartItem> getCart() {
        return cart;
    }


    public void orderCart() {
        IOHandler.printOrder(cart, "아래와 같이 주문하시겠습니까?");
        IOHandler.printOrderConfirm(totalPrice);
        int input = IOHandler.inputMenu(1, 2);
        // 1. 주문 확정  2. 메인으로 돌아가기
        if (input == 1) {
            calculateFinalPrice();
            for (CartItem product : cart) { // 재고 관리
                product.getItem().setQuantity(product.getItem().getQuantity() - product.getItemAmount());
            }
            this.deleteWholeCart(); // 장바구니 비우기
        }
        System.out.println();
    }

    public void deleteCart() {
        // 1. 부분 삭제, 2.전체 삭제, 0. 뒤로가기
        System.out.println("1. 부분 삭제\t2. 전체 삭제\t0. 뒤로가기");
        int menuSelect = IOHandler.inputMenu(0, 2);
        if (menuSelect == 1) { // 1. 부분 삭제
            IOHandler.printOrder(cart, "");

            int productSelect = IOHandler.inputMenu(1, cart.size());
            IOHandler.printCurrentProduct(cart.get(productSelect - 1).getItem());
            IOHandler.printConfirmMessage("정말 삭제하시겠습니까?");
            int confirmSelect = IOHandler.inputMenu(1, 2); // 1. 확인 2. 취소
            if (confirmSelect == 1)
                deleteCartProduct(cart.get(productSelect - 1).getItem());

        } else if (menuSelect == 2) { // 2. 전체 삭제
            IOHandler.printConfirmMessage("정말 삭제하시겠습니까?");
            deleteWholeCart();
        }
    }

    public void deleteWholeCart() {
        while (!cart.isEmpty()){
            cart.remove(cart.size()-1);
        }
        totalPrice = 0;
    }

    public void deleteCartProduct(Product product) {
        cart.stream()
                .filter(item -> item.getItemName().equals(product.getName()))
                .toList()
                .forEach(p -> {
                    totalPrice -= p.getItemPrice();
                    cart.remove(p);
                });
    }

    public void calculateFinalPrice() {
        IOHandler.printGradeMenu();
        CustomerGrade grade;
        int gradeSelect = IOHandler.inputMenu(1, 4);
        switch (gradeSelect) {
            case 2:
                grade = CustomerGrade.SILVER;
                break;
            case 3:
                grade = CustomerGrade.GOLD;
                break;
            case 4:
                grade = CustomerGrade.PLATINUM;
                break;
            default:
                grade = CustomerGrade.BRONZE;
        }
        IOHandler.printOrderComplete(cart, totalPrice, grade);
        deleteWholeCart();
    }
}
