package com.likelion13th.shop.service;

import com.likelion13th.shop.Exception.OutOfStockException;
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

import static com.likelion13th.shop.entity.QItem.item;
import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;
    public Long saveItem(ItemFormDto itemFormDto) throws Exception{
        Item item = itemFormDto.createItem();
        itemRepository.save(item);
        return item.getId();
    }
    @Value("${uploadPath}")
    private String uploadPath;

    public Long saveItem(ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception{
        Item item = itemFormDto.createItem();

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString()+"-"+itemImg.getOriginalFilename();
        File itemImgFile = new File(uploadPath, fileName);
        itemImg.transferTo(itemImgFile);
        item.setItemImg(fileName);
        item.setItemImgPath(uploadPath+"/"+fileName);

        itemRepository.save(item);
        return item.getId();
    }

    public List<ItemFormDto> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.of(s)));
        return itemFormDtos;
    }

    public ItemFormDto getItemById(Long itemId){
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isPresent()) {
            // 아이템 찾았을 경우 ItemFormDto로 반환
            return optionalItem.map(ItemFormDto::of).get();
        } else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다. "+itemId);
        }
    }

    //상품명으로 상품 조회
    public List<ItemFormDto> getItemsByName(String itemName){
        //해당 itemName을 포함하는 아이템 리스트 반환하여 저장
        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(itemName);

        List<ItemFormDto> itemFormDtos = new ArrayList<>(); //엔티티 리스트 -> dto 리스트로 변경 시 담을 dto 리스트 선언

        for (Item item : items){
            itemFormDtos.add(ItemFormDto.of(item)); //각 아이템을 DTO로 변환하여 리스트에 추가
        }

        return itemFormDtos; //dto 리스트

    }

    //상품 수정
    public void updateItem(Long itemId, ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        //필요한 필드만 업데이트
        if (optionalItem.isPresent()){
            Item item = optionalItem.get(); //객체
            if (itemFormDto.getItemName() != null){
                item.setItemName(itemFormDto.getItemName());
            }
            if (itemFormDto.getItemDetail() != null){
                item.setItemDetail(itemFormDto.getItemDetail());
            }
            if (itemFormDto.getSellStatus() !=null){
                item.setSellStatus(itemFormDto.getSellStatus());
            }
            if (itemFormDto.getStock() !=null){
                item.setStock(itemFormDto.getStock());
            }
            if (itemImg !=null){
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString()+"-"+itemImg.getOriginalFilename();
                File itemImgFile = new File(uploadPath, fileName);
                itemImg.transferTo(itemImgFile);
                item.setItemImg(fileName);
                item.setItemImgPath(uploadPath+"/"+fileName);
            }
            itemRepository.save(item);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다."+itemId);
        }
    }

    //상품 삭제:delete() 사용
    public void deleteItem(Long itemId){
        //삭제할 상품을 아이디로 조회
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        //itemId로 Item 찾기 -> 있으면 delete() 메소드로 삭제
        if(optionalItem.isPresent()){ //존재하면?
            itemRepository.delete(optionalItem.get());
        } else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다."+itemId);
        }
    }


}