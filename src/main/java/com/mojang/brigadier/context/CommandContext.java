package com.mojang.brigadier.context;

// Stub class for compilation
public class CommandContext<S> {
    private S source;
    
    public CommandContext(S source) {
        this.source = source;
    }
    
    public S getSource() {
        return source;
    }
}
