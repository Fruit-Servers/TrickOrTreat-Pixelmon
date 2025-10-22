package net.minecraft.nbt;

import java.util.ArrayList;
import java.util.List;

// Stub class for compilation
public class ListNBT {
    private List<Object> list = new ArrayList<>();
    
    public void add(Object value) {
        list.add(value);
    }
    
    public int size() {
        return list.size();
    }
    
    public Object get(int index) {
        return list.get(index);
    }
}
