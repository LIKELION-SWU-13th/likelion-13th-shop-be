package com.likelion13th.shop.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// 장바구니 상품 정보
public class CartDetailDto {
    //장바구니 상품 아이디
    private Long cartItemId;

    //상품명
    private String itemName;

    //상품 금액
    private int price;

    // 상품 수량
    private int count;

    //상품 이미지 경로
    private String itemImgPath;

    // 객체 생성자 함수
    public CartDetailDto(Long cartItemId, String itemName, int price, int count, String itemImgPath){
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price =price;
        this.count=count;
        this.itemImgPath=itemImgPath;
    }
}
