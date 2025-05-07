package com.likelion13th.shop.service;

import com.likelion13th.shop.ItemFormDto.ItemFormDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.QItem;
import com.likelion13th.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

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
        Item item=itemFormDto.createItem();
        UUID uuid=UUID.randomUUID();
        String fileName=uuid.toString()+"-"+itemImg.getOriginalFilename();
        File itemImgFile=new File(uploadPath,fileName);
        itemImg.transferTo(itemImgFile);
        item.setItemImg(fileName);
        item.setItemImgPath(uploadPath+"/"+fileName);
        itemRepository.save(item);

        return item.getId();
    }

    public List<ItemFormDto> getItems(){
        List<Item> items = itemRepository.findAll();
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.of(s)));
        return itemFormDtos;
    }

    public ItemFormDto getItemById(Long itemId){
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if(optionalItem.isPresent()){
            return ItemFormDto.of(optionalItem.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다."+itemId);
        }
    }

    public List<ItemFormDto> getItemsByName(String itemName){
        List<Item> items = itemRepository.findByItemNameContainingIgnoreCase(itemName);
        List<ItemFormDto> itemFormDtos = new ArrayList<>();
        items.forEach(s -> itemFormDtos.add(ItemFormDto.of(s)));
        return itemFormDtos;
    }

    public void updateItem(Long itemId, ItemFormDto itemFormDto, MultipartFile itemImg) throws Exception{
        Optional<Item> optionalItem = itemRepository.findById(itemId);

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
            if (itemImg != null) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid.toString() + "_" + itemImg.getOriginalFilename();
                File itemImgFile = new File(uploadPath, fileName);
                itemImg.transferTo(itemImgFile);
                item.setItemImg(fileName);
                item.setItemImgPath(uploadPath + "/" + fileName);
            }

            itemRepository.save(item);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }

    public void deleteItem(Long itemId){
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            itemRepository.delete(optionalItem.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID에 해당하는 상품을 찾을 수 없습니다." + itemId);
        }
    }
}