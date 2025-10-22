package net.minecraft.item;

import net.minecraft.nbt.CompoundNBT;

// Stub class for compilation - will be replaced by actual Minecraft at runtime
public class ItemStack {
    private Item item;
    private int count;
    private CompoundNBT tag;
    
    public ItemStack(Item item) {
        this(item, 1);
    }
    
    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }
    
    public CompoundNBT getOrCreateTag() {
        if (tag == null) {
            tag = new CompoundNBT();
        }
        return tag;
    }
    
    public CompoundNBT getTag() {
        return tag;
    }
    
    public void setTag(CompoundNBT tag) {
        this.tag = tag;
    }
    
    public boolean isEmpty() {
        return count <= 0;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public void shrink(int amount) {
        this.count -= amount;
    }
    
    public Item getItem() {
        return item;
    }
}
