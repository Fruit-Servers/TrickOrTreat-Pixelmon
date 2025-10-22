package net.minecraft.nbt;

// Stub class for compilation
public class StringNBT {
    private String value;
    
    private StringNBT(String value) {
        this.value = value;
    }
    
    public static StringNBT valueOf(String value) {
        return new StringNBT(value);
    }
    
    public String getAsString() {
        return value;
    }
}
