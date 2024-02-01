package com.delish.Menu.utility;

import com.delish.Menu.Menu;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Component
public class FieldCheckUtility {

    public List<Field> getNullFields(Object o){
        List<Field> nonNullFields = new LinkedList<>();
        Field[] fields = o.getClass().getFields();
        for(Field f : fields){
            if(f!=null){
                nonNullFields.add(f);
            }
        }
        return nonNullFields;
    }
}
