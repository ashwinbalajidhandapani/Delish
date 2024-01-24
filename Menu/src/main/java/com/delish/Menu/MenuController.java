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
    public MenuIdResponse createMenu(@RequestBody Menu menu){
        Integer menuId = menuService.createMenu(menu);
        return new MenuIdResponse(menuId);
    }

    @GetMapping
    public List<Menu> getMenuAll(){
        return menuService.getAllMenu();
    }



}
