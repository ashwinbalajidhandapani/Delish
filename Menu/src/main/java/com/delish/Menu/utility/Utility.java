package com.delish.Menu.utility;

import com.delish.Menu.Item;
import com.delish.Menu.ItemService;
import com.delish.Menu.Menu;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class Utility {
    private FieldCheckUtility fieldCheckUtility;
    private ItemService itemService;



    public Menu buildMenu(Menu menu){
        List<Field> nonNullableFields = fieldCheckUtility.getNullFields(menu);
        Menu menuBuilder = Menu.builder().build();
        for(Field f : nonNullableFields){
            if(f.equals(menu.getId())){
                menuBuilder.setId(menu.getId());
            }
            else if(f.equals(menu.getName())){
                menuBuilder.setName(menu.getName());
            }
            else if(f.equals(menu.getDescription())){
                menuBuilder.setDescription(menu.getDescription());
            }
            else if(f.equals(menu.getRestaurantid())){
                menuBuilder.setRestaurantid(menu.getRestaurantid());
            }
            else if(f.equals(menu.getItems())){
                for(Item i : menu.getItems()){
                    itemService.createItem(i);
                }
                menuBuilder.setItems(menu.getItems());
            }
        }
        return menuBuilder;
    }

    public Item buildItem(Item item){
        List<Field> nonNullableFields = fieldCheckUtility.getNullFields(item);
        Item itemBuilder = Item.builder().build();
        for(Field f : nonNullableFields){
            if(f.equals(item.getId())){
                itemBuilder.setId(item.getId());
            }
            else if(f.equals(item.getName())){
                itemBuilder.setName(item.getName());
            }
            else if(f.equals(item.getDescription())){
                itemBuilder.setDescription(item.getDescription());
            }
            else if(f.equals(item.getMenuId())){
                itemBuilder.setMenuId(item.getMenuId());
            }
            else if(f.equals(item.getPrice())){
                itemBuilder.setPrice(item.getPrice());
            }
        }
        return itemBuilder;
    }
}
