package net.minecraft.nbt;

import java.util.HashMap;
import java.util.Map;

// Stub class for compilation
public class CompoundNBT {
    private Map<String, Object> data = new HashMap<>();
    
    public void putString(String key, String value) {
        data.put(key, value);
    }
    
    public String getString(String key) {
        Object value = data.get(key);
        return value instanceof String ? (String) value : "";
    }
    
    public boolean contains(String key) {
        return data.containsKey(key);
    }
    
    public void put(String key, Object value) {
        data.put(key, value);
    }
    
    public CompoundNBT getCompound(String key) {
        Object value = data.get(key);
        if (value instanceof CompoundNBT) {
            return (CompoundNBT) value;
        }
        CompoundNBT compound = new CompoundNBT();
        data.put(key, compound);
        return compound;
    }
}
