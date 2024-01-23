package com.delish.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @SequenceGenerator(
            name = "restaurant_id_sequence",
            sequenceName = "restaurant_id_sequence"
    )
    private Long id;
    private String name;
    private String address;
    private Boolean isopen;
    private String menuid;
}
