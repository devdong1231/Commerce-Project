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
        // 재고 충분한지 확인
        if (product.getQuantity() < 1) {
            System.out.println("재고가 부족합니다.");
            return;
        }

        IOHandler.printCurrentProduct(product);
        IOHandler.printConfirmMessage("위 상품을 장바구니에 추가하시겠습니까?");

        // 1. 예 2. 아니오
        int input = IOHandler.inputMenu(1, 2);

        if (input == 2) // 아니오
            return;

        // 카트에 상품 존재 -> 수량만 추가 / 카트에 상품 존재X -> 상품 추가
        if (cart.isEmpty()) {
            cart.add(new CartItem(product));
        } else {
            boolean isExist = false;
            for (CartItem item : cart) {
                if (item.getItemName().equals(product.getName())) { // 장바구니에 담겨 있는 상품이면 찾아서 개수만 늘리기
                    isExist = true;
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

    public List<CartItem> getCart() {
        return cart;
    }


    public void orderCart() {
        IOHandler.printOrder(cart, "아래와 같이 주문하시겠습니까?");
        IOHandler.printOrderConfirm(totalPrice);
        int input = IOHandler.inputMenu(1, 2);
        // 1. 주문 확정  2. 메인으로 돌아가기
        if (input == 2)
            return;

        calculateFinalPrice();
        for (CartItem product : cart) { // 재고 관리
            product.getItem().setQuantity(product.getItem().getQuantity() - product.getItemAmount());
        }
        this.deleteWholeCart(); // 장바구니 비우기

    }

    public void deleteCart() {
        // 메뉴 등록
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("부분 삭제", () -> {
            IOHandler.printOrder(cart, "");
            int productSelect = IOHandler.inputMenu(1, cart.size());
            IOHandler.printCurrentProduct(cart.get(productSelect - 1).getItem());
            IOHandler.printConfirmMessage("정말 삭제하시겠습니까?");
            int confirmSelect = IOHandler.inputMenu(1, 2); // 1. 확인 2. 취소
            if (confirmSelect == 1)
                deleteCartProduct(cart.get(productSelect - 1).getItem());
        }));
        menu.add(new MenuItem("전체 삭제", () -> {
            IOHandler.printConfirmMessage("정말 삭제하시겠습니까?");
            deleteWholeCart();
        }));
        // 메뉴 출력
        IOHandler.printDeleteCart(menu);
        // 메뉴 입력
        int menuSelect = IOHandler.inputMenu(0, 2);
        // 메뉴 실행
        menu.get(menuSelect - 1).execute();
    }

    private void deleteWholeCart() {
        while (!cart.isEmpty())
            cart.remove(cart.size() - 1);
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
        // 메뉴 등록
        List<MenuItem> menu = new ArrayList<>();
        for (CustomerGrade grade : CustomerGrade.values()) {
            menu.add(new MenuItem(grade.toString(), () -> {
                IOHandler.printOrderComplete(cart, totalPrice, grade);
                deleteWholeCart();
            }));
        }
        // 메뉴 출력
        IOHandler.printGradeMenu(menu);
        //메뉴 입력
        int gradeSelect = IOHandler.inputMenu(1, 4);
        //메뉴 실행
        menu.get(gradeSelect - 1).execute();
    }
}