package com.delish.Menu;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document
public class Menu {
    @Id
    private Integer id;
    private String name;
    private String description;
    private List<Item> items;
    private Long restaurantid;
}
