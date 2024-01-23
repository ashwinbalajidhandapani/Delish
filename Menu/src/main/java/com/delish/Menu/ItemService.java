package com.delish.Menu;

import org.springframework.stereotype.Service;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public void createItem(Item item){
        itemRepository.save(item);
    }
}
