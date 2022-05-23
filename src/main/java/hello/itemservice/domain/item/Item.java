package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


// @Data를 쓰는 것보다 구체적으로 사용(불필요한 동작이 발생할 수 있기 때문
//@Getter
//@Setter
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;  // Integer 사용 이유 : price가 없는 경우(null)도 고려
   private Integer quantity;

   public Item(){}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
