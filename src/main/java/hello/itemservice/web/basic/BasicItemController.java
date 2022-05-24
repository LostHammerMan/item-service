package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;


//    @Autowired // 생성자가 하나만 있는 경우 생략 가능
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
    //  --> @RequiredArgsConstructor 로 대체 가능

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 30));
        itemRepository.save(new Item("itemB", 20000, 50));
    }

    // 상품 상세

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품 등록 form 보기
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    // 같은 URL이지만 메서드로 구분됨
    // 상품 등록
    //@PostMapping("/add")
    public String additemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);


        return "basic/item";
    }

    // @ModelAttribute 사용
    //@PostMapping("/add")
    public String additemV2(@ModelAttribute("item") Item item,
                       Model model){
//        Item item = new Item(); -> @ModelAttribute에 의해 만들어짐, set도 호출됨


        itemRepository.save(item);
//        model.addAttribute("item", item); -> @ModelAttribute에 의해 만들어짐


        return "basic/item";
    }

    //@ModelAttribute 이름 생략
    //@PostMapping("/add")
    public String additemV3(@ModelAttribute Item item){
        itemRepository.save(item); // 생략시 model에 저장되는  name은 클래스명 첫글자만 소문자로 등록
        return "basic/item";
    }

    //@ModelAttribute 자체 생략, 임의의 객체의 경우 @ModelAttribute 적용됨(String 같은 단순형 제외)
    //@PostMapping("/add")
    public String additemV4(Item item){
        itemRepository.save(item); // 샹략시 model에 저장되는  name은 클래스명 첫글자만 소문자로 등록
        return "basic/item";
    }

    // 새로고침 문제 해결 버전
    //@PostMapping("/add")
    public String additemV5(Item item){
        itemRepository.save(item); // 샹략시 model에 저장되는  name은 클래스명 첫글자만 소문자로 등록
        return "redirect:/basic/items/" + item.getId();
    }

    // RedirectAttribute 적용
    @PostMapping("/add")
    public String additemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); //
        return "redirect:/basic/items/{itemId}";
    }




    // 상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping ("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

}