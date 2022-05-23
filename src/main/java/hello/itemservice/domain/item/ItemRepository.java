package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L; // static을 안하면 싱글톤 깨짐

    // item 저장 기능
    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // 상품 조회 기능
    public Item findById(Long id){
        return store.get(id);


    }

    // 상품 목록
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    // 상품 수정
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 상품 전체 삭제
    public void clearStore(){
        store.clear();
    }
}
