# Commerce Project
 - 환경 : JDK 17

## 기능
- 상품 조회(가격별)
- 관리자 모드(상품 CRUD)
- 장바구니 추가
- 장바구니 삭제
- 상품 구매


## 클래스 소개
### Main.java
 - 시작 지점이 되는 클래스

### Admin.java
 - 관리자 기능에 대한 전반적인 기능이 들어있는 클래스
   - 상품 추가
   - 상품 수정
   - 상품 삭제
   - 전체 상품 조회

### Cart.java
 - 장바구니 기능을 담당하는 클래스
   - 장바구니 상품 추가
   - 장바구니에 담긴 상품 구매
     - 할인율 적용 후 최종 금액 출력
   - 장바구니 전체 비우기
   - 특정 상품 장바구니에서 삭제

### CartItem.java
 - 장바구니에 담길 아이템에 대한 정보를 저장하는 클래스
   - product : 상품
   - amount : 장바구니에 담은 갯수

### Category.java
 - 카테고리와 카테고리에 맞는 상품 목록을 가지는 클래스
   - categoryName : 카테고리 이름
   - productList : 상품 목록

### CommerceSystem.java
 - 커머스 시스템의 전반적인 것을 관리하는 클래스
   - mainMenu 호출
   - subMenu 호출
   - 관리자 모드 진입 시 로그인 시스템 호출

### Product.java
 - 개별 상품 정보를 가지는 클래스
   - productName : 상품 이름
   - price : 상품 가격
   - description : 상품 설명
   - quantity : 상품 재고 개수

### Cumstomer.java
 - 고객 정보를 가지는 클래스
   - customerName : 고객 이름
   - email : 고객 이메일
   - grade : 고객 등급

### IOHandler.java
 - 입출력을 관리하는 클래스
   - static으로 Scanner 객체를 하나만 생성해서 사용
   - 프로그램 종료 시 closeScanner()로 sc.close() 호출