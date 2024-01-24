package com.delish.Menu;



import com.delish.Menu.utility.Utility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ItemService itemService;

    private Utility utility = new Utility();

    public MenuService(MenuRepository menuRepository, ItemService itemService){
        this.menuRepository = menuRepository;
        this.itemService = itemService;
    }

    public Integer createMenu(Menu menu){

        Menu createdMenu = Menu.builder()
                .id(getCurrId()+1)
                .name(menu.getName())
                .description(menu.getDescription())
                .build();
        if(!menu.getItems().isEmpty()){
            List<Item> modifiedItems = new ArrayList<>();
            for (Item i : menu.getItems()){
                i.setMenuId(createdMenu.getId());
                modifiedItems.add(i);
            }
            createdMenu.setItems(modifiedItems);
        }
        menuRepository.save(createdMenu);
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

    public Integer getCurrId(){
        List<Menu> currMenuList = getAllMenu();
        Integer currHigh = 0;
        for (Menu m : currMenuList){
            if(m.getId() > currHigh) currHigh = m.getId();
        }
        return currHigh;
    }
}
