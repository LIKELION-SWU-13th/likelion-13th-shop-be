package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.service.ItemService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @PostMapping("/new")
    public ResponseEntity<Long> createItem(@RequestPart(name="itemFormDto")ItemFormDto itemFormDto,
                                           @RequestPart(value = "itemImg", required = false) MultipartFile itemImg){
        if (itemImg == null){
            try {
                Long itemId = itemService.saveItem(itemFormDto);

                return ResponseEntity.status(HttpStatus.CREATED).body(itemId);

            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            try{
                Long itemId = itemService.saveItem(itemFormDto, itemImg);
                return ResponseEntity.status(HttpStatus.CREATED).body(itemId);
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    //전체 상품 조회
    @RequestMapping
    public ResponseEntity<List<ItemFormDto>> getItems(){
        return ResponseEntity.ok().body(itemService.getItems());
    }


    //특정 상품 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemFormDto> getItemById(@PathVariable Long itemId){
        try{
            //폼으로 받은 아이템id를 통해 특정 상품조회
            ItemFormDto itemFormDto = itemService.getItemById(itemId);
            return ResponseEntity.ok().body(itemFormDto);
        } catch(HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //상품명으로 상품 조회
    @GetMapping("/search")
    public ResponseEntity<List<ItemFormDto>> searchItemsByName(@RequestParam String itemName){
        //반환형은 List<ItemFormDto>, 상품 목록 결과를 리스트로
        try{
            //상품명으로 상품 조회
            List<ItemFormDto> itemFormDtos = itemService.getItemsByName(itemName);
            return ResponseEntity.ok().body(itemFormDtos);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //기존 상품 수정
    @PatchMapping("/{itemId}") //상품 수정은 리소스를 갱신하므로 PUT 사용
    public ResponseEntity<String> updateItem(@PathVariable Long itemId,
                                             @ModelAttribute ItemFormDto itemFormDto,
                                             @RequestParam("itemImg") MultipartFile itemImg){
        //@Path-URL 경로에 포함된 itemId를 변수로 받음
        //@Model-form에 전달되는 상품 정보를 객체로 바인딩
        //@RequestParam-form-data 파일을 따로 받기 위함

        try{
            itemService.updateItem(itemId, itemFormDto, itemImg); //각 메소드 호출
            return ResponseEntity.ok().body("상품이 성공적으로 수정되었습니다.");
        } catch(HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body("상품을 찾을 수 없습니다.");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
        try {
            //상품 삭제
            itemService.deleteItem(itemId);
            return ResponseEntity.ok().body("상품이 성공적으로 삭제되었습니다.");
        } catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body("상품을 찾을 수 없습니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
