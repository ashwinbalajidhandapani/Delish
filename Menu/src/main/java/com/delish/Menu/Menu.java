package com.delish.Menu;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Menu {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private List<Item> items;
}
