package com.delish.Menu;

import com.delish.Menu.utility.Utility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private Utility utility = new Utility();

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Integer createItem(Item item){
        Item createdItem = utility.buildItem(item);
        itemRepository.save(createdItem);
        return createdItem.getId();
    }

    public List<Item> displayItems(Integer menuid){
        List<Item> resultingItemList = new ArrayList<>();
        for (Item i : itemRepository.findAll()) {
            if(i.getMenuId() == menuid){
                resultingItemList.add(i);
            }
        }
        return resultingItemList;
    }

    public Integer getCurrentId(){
        try {
            List<Item> currList = itemRepository.findAll();
            if(currList.isEmpty()){
                return 0;
            }
            int currHigh = 0;
            for (Item i : currList) {
                int currId = i.getId();
                if (currId > currHigh) currHigh = currId;
            }
            return currHigh;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return 0;
        }
    }


}
