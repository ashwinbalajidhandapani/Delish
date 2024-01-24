package com.delish.Menu;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Item {
    @Id
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer menuId;
}
