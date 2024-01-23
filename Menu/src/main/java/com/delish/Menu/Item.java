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
    private ObjectId id;
    private String itemName;
    private String itemDescription;
    private Double price;
}
