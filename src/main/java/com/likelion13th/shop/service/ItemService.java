package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    @Autowired  // 생성자 주입
    private final ItemRepository itemRepository;

    public Long saveItem(ItemFormDto itemFormDto) throws Exception{
        //상품 등록
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
        item.setItemImgPath(uploadPath + "/" + fileName);
        itemRepository.save(item);

        return item.getId();
    }

    public List<ItemFormDto> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.cof(s)));
        return itemFormDtos;
    }

    public ItemFormDto getItemById(Long itemId) {
        // 아이템 ID로 아이템을 조회
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isPresent()) {
            // 아이템을 찾았을 경우 ItemFormDto로 반환
            return ItemFormDto.cof(optionalItem.get());
        } else {
            // 아이템을 찾지 못했을 경우 예외 처리
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다. " + itemId);
        }
    }

    // 상품명으로 상품 조회
    public List<ItemFormDto> getItemsByName(String itemName) {
        // 쿼리메소드 호출하여 해당하는 아이템 리스트 반환하여 저장
        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(itemName);
        // 엔티티 리스트 -> dto 리스트로 변경시 담을 dto 리스트 선언
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        // 아이템 리스트를 순회하며 dto로 변환하여 선언된 리스트에 담음
        items.forEach(s -> itemFormDtos.add(ItemFormDto.cof(s)));   // 각 상품 정보를 ItemFormDto로 변환하여 리스트에 추가
        // dto 리스트 반환
        return itemFormDtos;
    }

    // 상품 수정
    public void updateItem(Long itemId, ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        // 업데이트할 필드만 업데이트
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            if (itemFormDto.getItemName() != null) {
                item.setItemName(itemFormDto.getItemName());
            }
            if (itemFormDto.getPrice() != null) {
                item.setPrice(itemFormDto.getPrice());
            }
            if (itemFormDto.getItemDetail() != null) {
                item.setItemDetail(itemFormDto.getItemDetail());
            }
            if (itemFormDto.getItemSellStatus() != null) {
                item.setItemSellStatus(itemFormDto.getItemSellStatus());
            }
            if (itemFormDto.getStock() != null) {
                item.setStock(itemFormDto.getStock());
            }

            // 이미지 업데이트
            if (itemImg != null) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString() + "-" + itemImg.getOriginalFilename();
                File itemImgFile = new File(uploadPath, fileName);
                itemImg.transferTo(itemImgFile);
                item.setItemImg(fileName);
                item.setItemImgPath(uploadPath + "/" + fileName);
            }

            // 수정된 상품 저장
            itemRepository.save(item);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }

    // 상품 삭제
    public void deleteItem(Long itemId) {
        // 삭제할 상품을 아이디로 조회
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isPresent()) {
            // 상품이 존재하면 삭제
            itemRepository.delete(optionalItem.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "IDㅇ에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }
}