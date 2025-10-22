package net.minecraft.command;

// Stub class for compilation
public class CommandSource {
    public boolean hasPermission(int level) {
        return true; // Stub implementation
    }
    
    public void sendSuccess(Object message, boolean broadcastToOps) {
        System.out.println("Command success: " + message);
    }
    
    public void sendFailure(Object message) {
        System.out.println("Command failure: " + message);
    }
}
