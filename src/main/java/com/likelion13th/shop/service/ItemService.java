package com.likelion13th.shop.service;

import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long saveItem(ItemFormDto itemFormDto) throws Exception{
        // 상품 등록
        Item item = itemFormDto.createItem();

        itemRepository.save(item);

        return item.getId();
    }

    @Value("${uploadPath}")
    private String uploadPath;

    public Long saveItem(ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception{
        Item item = itemFormDto.createItem();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "-" + itemImg.getOriginalFilename();
        File itemImgFile = new File(uploadPath, fileName);
        itemImg.transferTo(itemImgFile);
        item.setItemImg(fileName);
        item.setItemImgPath(uploadPath+"/"+fileName);
        itemRepository.save(item);

        return item.getId();
    }

    // 전체 상품 조회
    public List<ItemFormDto> getItems(){
        List<Item> items = itemRepository.findAll();
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.of(s)));
        return itemFormDtos;
    }

    // 특정 상품 조회
    public ItemFormDto getItemById(Long itemId){
        // 아이템 ID로 아이템을 조회
        System.out.println("getItemById");
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if(optionalItem.isPresent()){
            System.out.println("getItemById1");
            // 아이템 찾았을 경우 ItemFormDto로 반환
            return ItemFormDto.of(optionalItem.get());
        }else{
            System.out.println("getItemById2");
            // 아이템을 찾지 못했을 경우 예외 처리
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다."+itemId);
        }
    }

    // 상품명으로 상품 조회
    public List<ItemFormDto> getItemsByName(String itemName){
        // 쿼리메소드 호출하여 해당하는 아이템 리스트 반환하여 저장
        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(itemName);
        // 엔터티 리스트 -> dto 리스트로 변경 시 담을 dto 리스트 선언
        List<ItemFormDto> itemDtoList = new ArrayList<>();
        // 아이템 리스트를 순회하며 dto로 변환하여 선언된 리스트에 담음
        for (Item item : items) {
            ItemFormDto dto = ItemFormDto.of(item);
            itemDtoList.add(dto);
        }
        // dto 리스트 반환
        return itemDtoList;
    }

    // 상품 수정
    public void updateItem(Long itemId, ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception{
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        // 업데이트할 필드만 업데이트
        if(optionalItem.isPresent()){
            Item item = optionalItem.get();

            if(itemFormDto.getItemName() != null){
                item.setItemName(itemFormDto.getItemName());
            }
            if(itemFormDto.getPrice() != null){
                item.setPrice(itemFormDto.getPrice());
            }
            if(itemFormDto.getItemDetail() != null){
                item.setItemDetail(itemFormDto.getItemDetail());
            }
            if(itemFormDto.getItemSellStatus() != null){
                item.setItemSellStatus(itemFormDto.getItemSellStatus());
            }
            if(itemFormDto.getStock() != null){
                item.setStock(itemFormDto.getStock());
            }
            if(itemImg != null){
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString() + "-" + itemImg.getOriginalFilename();
                File itemImgFile = new File(uploadPath, fileName);
                itemImg.transferTo(itemImgFile);
                item.setItemImg(fileName);
                item.setItemImgPath(uploadPath+"/"+fileName);
            }
            // 수정된 상품 저장
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }

    // 상품 삭제
    public void deleteItem(Long itemId){
        // 삭제할 상품을 아이디로 조회
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if(optionalItem.isPresent()){
            // 상품이 존재하면 삭제
            itemRepository.delete(optionalItem.get());
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }

}

