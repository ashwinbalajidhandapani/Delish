package com.delish.Menu;



import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ItemService itemService;

    public MenuService(MenuRepository menuRepository, ItemService itemService){
        this.menuRepository = menuRepository;
        this.itemService = itemService;
    }

    public ObjectId createMenu(Menu menu){
        Menu createdMenu = menuRepository.save(menu);
        return createdMenu.getId();
    }

    public List<Menu> getAllMenu(){
        return menuRepository.findAll();
    }

    public List<Menu> getMenuByName(String menuName){
        List<Menu> searchMatch = new ArrayList<>();
        for (Menu menu: getAllMenu()) {
            if(menu.equals(menuName)){
                searchMatch.add(menu);
            }
        }
        return searchMatch;
    }
}
