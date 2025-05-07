package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    // 새로운 아이템 등록
    @PostMapping("/new")
    public ResponseEntity<Long> createItem(@RequestPart(name = "itemFormDto") ItemFormDto itemFormDto, @RequestPart(value = "itemImg", required = false) MultipartFile itemImg) {
        try {
            Long itemId;
            if (itemImg == null) {
                itemId = itemService.saveItem(itemFormDto); // 이미지 없는 상품 등록
            } else {
                itemId = itemService.saveItem(itemFormDto, itemImg); // 이미지 있는 상품 등록
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
        } catch (Exception e) {
            logger.error("아이템 등록 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ItemFormDto>> getItems() {
        try {
            List<ItemFormDto> items = itemService.getItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("전체 상품 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 상품 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemFormDto> getItemById(@PathVariable("itemId") Long itemId) {
        try {
            ItemFormDto itemFormDto = itemService.getItemById(itemId);
            return ResponseEntity.ok(itemFormDto);
        } catch (Exception e) {
            logger.error("특정 아이템 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 상품명으로 상품 조회
    @GetMapping("/search")
    public ResponseEntity<List<ItemFormDto>> searchItemsByName(@RequestParam("itemName") String itemName) {
        try {
            List<ItemFormDto> items = itemService.getItemsByName(itemName);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("상품명으로 상품 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 기존 상품 수정
    @PatchMapping("/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("itemId") Long itemId,
                                             @RequestPart(name = "itemFormDto") ItemFormDto itemFormDto,
                                             @RequestPart(value = "itemImg", required = false) MultipartFile itemImg) {
        try {
            itemService.updateItem(itemId, itemFormDto, itemImg);
            return ResponseEntity.ok("상품이 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("상품 수정 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품을 찾을 수 없습니다.");
        }
    }


    // 기존 상품 삭제
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") Long itemId) {
        try {
            itemService.deleteItem(itemId);
            return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("상품 삭제 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품을 찾을 수 없습니다");
        }
    }
}
