package nl.fontys.sofa.limo.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.validation.annotations.Size;


public class SizeBean {
    
    @Size(min=2, max=4)
    Map<String, String> map = new HashMap<>();
    
    @Size(min=2, max=4)
    String[] array = {"a", "b", "c"};
    
    @Size(min=2, max=4)
    String text = "hi!";
    
    @Size(min=2, max=4)
    List<String> list = new ArrayList<>();
    
    public SizeBean() {
        map.put("a", "c");
        map.put("b", "b");
        map.put("c", "a");
        list.add("a");
        list.add("b");
        list.add("c");
    }
}
