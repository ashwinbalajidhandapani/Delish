package com.delish.Menu;



import com.delish.Menu.utility.Utility;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ItemService itemService;


    private final MongoTemplate mongoTemplate;
    private Utility utility = new Utility();

    public MenuService(MenuRepository menuRepository, ItemService itemService, MongoTemplate mongoTemplate){
        this.menuRepository = menuRepository;
        this.itemService = itemService;
        this.mongoTemplate = mongoTemplate;
    }

//    public boolean isServerRunning(){
//        db.runCommand({ serverStatus: 1 })
//    }

    public Integer createMenu(Menu menu){
        Menu createdMenu = utility.buildMenu(menu);
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
        if(currMenuList.size() == 0) return 0;
        for (Menu m : currMenuList){
            if(m.getId() > currHigh) currHigh = m.getId();
        }
        return currHigh;
    }

    public Integer createMenuOnlyWithRestaurantId(Long id){
        Integer currId = getCurrId();
        Menu menuDummy = Menu.builder()
                        .id(currId+1)
                        .items(new ArrayList<Item>())
                        .restaurantid(id).build();
        menuRepository.save(menuDummy);
        return menuDummy.getId();
    }

    public Menu matchMenuWithRestautantIdAndMenuName(String name, Integer id){
        Query query = new Query()
                .addCriteria(Criteria.where("name").is(name).and("restaurantid").is(id));
        List<Menu> matchedMenu = mongoTemplate.find(query, Menu.class);
        if(matchedMenu == null || matchedMenu.isEmpty()) throw new NoSuchElementException();
        return matchedMenu.get(0);
    }

    public Integer findIdByName(String name){
        Query query = new Query()
                .addCriteria(Criteria.where("name").is(name));
        List<Menu> matches =
    }
    public void addItemByMenuId(){

    }

}
