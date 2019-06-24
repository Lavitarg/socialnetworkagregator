package service.repo;

import org.springframework.stereotype.Component;
import service.models.Item;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemsRepo {
    static List<Item> items = new ArrayList<>();
    public void addItems(Item item) {
        this.items.add(item);
    }
    public  List<Item> findAll(){return items;}
}
