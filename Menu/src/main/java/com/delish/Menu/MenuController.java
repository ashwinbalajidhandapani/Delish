package com.delish.Menu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="/v1/api/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu){
        menuService.createMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }

    @GetMapping
    public List<Menu> getMenuAll(){
        return menuService.getAllMenu();
    }

    @PostMapping(path = "update_restaurant_info")
    public Integer createMenuOnlyWithRestaurantId(@RequestParam Long restaurantid){
        return menuService.createMenuOnlyWithRestaurantId(restaurantid);
    }


}
