package com.halloween.trickortreat;

import com.halloween.trickortreat.commands.TrickOrTreatCommand;
import com.halloween.trickortreat.commands.TriggerCandyDropCommand;
import com.halloween.trickortreat.config.ConfigManager;
import com.halloween.trickortreat.items.CandyManager;
import com.halloween.trickortreat.listeners.PixelmonEventListener;
import com.halloween.trickortreat.managers.CooldownManager;
import com.halloween.trickortreat.rewards.RareTreatManager;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod("trickortreatpixelmon")
public class TrickOrTreatPixelmonMod {
    
    public static final String MODID = "trickortreatpixelmon";
    public static final String VERSION = "1.2.0-pixelmon";
    
    private static TrickOrTreatPixelmonMod instance;
    private ConfigManager configManager;
    private CandyManager candyManager;
    private CooldownManager cooldownManager;
    private PixelmonEventListener pixelmonEventListener;
    private RareTreatManager rareTreatManager;
    private TriggerCandyDropCommand triggerCandyDropCommand;
    
    public TrickOrTreatPixelmonMod() {
        instance = this;
        initialize();
    }
    
    private void initialize() {
        System.out.println("🎃 Halloween Trick or Treat Pixelmon mod v" + VERSION + " is starting!");
        
        // Initialize configuration first
        this.configManager = new ConfigManager();
        
        // Initialize candy system
        this.candyManager = new CandyManager(this);
        
        // Initialize cooldown system
        this.cooldownManager = new CooldownManager(this);
        
        // Initialize Pixelmon event listener
        this.pixelmonEventListener = new PixelmonEventListener(this);
        
        // Initialize rare treat system
        this.rareTreatManager = new RareTreatManager(this);
        
        // Initialize commands
        this.triggerCandyDropCommand = new TriggerCandyDropCommand(this);
        
        System.out.println("🎃 Configuration loaded successfully!");
        System.out.println("🎃 Candy system initialized!");
        System.out.println("🎃 Cooldown system initialized!");
        System.out.println("🎃 Pixelmon event listener initialized!");
        System.out.println("🎃 Rare treat system initialized!");
        System.out.println("🎃 Commands registered!");
        System.out.println("🎃 This mod adds Halloween candy drops when defeating/catching Pixelmon!");
        System.out.println("🎃 Features: Rare treats, cooldowns, custom items, and more!");
        System.out.println("🎃 Ready for integration with Pixelmon Reforged 9.1.11+");
    }
    
    public static TrickOrTreatPixelmonMod getInstance() {
        if (instance == null) {
            instance = new TrickOrTreatPixelmonMod();
        }
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public CandyManager getCandyManager() {
        return candyManager;
    }
    
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
    
    public PixelmonEventListener getPixelmonEventListener() {
        return pixelmonEventListener;
    }
    
    public RareTreatManager getRareTreatManager() {
        return rareTreatManager;
    }
    
    public TriggerCandyDropCommand getTriggerCandyDropCommand() {
        return triggerCandyDropCommand;
    }
    
//    // Main entry point for testing
//    public static void main(String[] args) {
//        System.out.println("Testing TrickOrTreatPixelmonMod...");
//        TrickOrTreatPixelmonMod mod = new TrickOrTreatPixelmonMod();
//
//        // Test candy creation
//        System.out.println("\n=== Testing Candy System ===");
//        mod.getCandyManager().createRegularCandy();
//        mod.getCandyManager().createRareCandy();
//
//        // Test cooldown system
//        System.out.println("\n=== Testing Cooldown System ===");
//        UUID testPlayer = UUID.randomUUID();
//        System.out.println("Test player UUID: " + testPlayer);
//
//        // Test rare candy cooldown
//        System.out.println("Is on rare candy cooldown: " + mod.getCooldownManager().isOnRareCandyCooldown(testPlayer));
//        mod.getCooldownManager().setRareCandyCooldown(testPlayer);
//        System.out.println("Is on rare candy cooldown after setting: " + mod.getCooldownManager().isOnRareCandyCooldown(testPlayer));
//
//        long remaining = mod.getCooldownManager().getRareCandyCooldownRemaining(testPlayer);
//        System.out.println("Remaining time: " + mod.getCooldownManager().formatCooldownTime(remaining));
//
//        System.out.println(mod.getCooldownManager().getCooldownStats());
//
//        System.out.println("\nSimulating mythical Pixelmon defeat:");
//        mod.getPixelmonEventListener().simulatePixelmonDefeat(false, true, false);
//
//        // Test rare treat system
//        System.out.println("\n=== Testing Rare Treats ===");
//        System.out.println(mod.getRareTreatManager().getTreatStats());
//
//        System.out.println("Testing rare treat activation:");
//        mod.getRareTreatManager().activateRareTreat("TestPlayer");
//
//        System.out.println("\nTesting forced treats:");
//        mod.getRareTreatManager().forceGiveTreat("TestPlayer", "netherite");
//        mod.getRareTreatManager().forceGiveTreat("TestPlayer", "token");
//        mod.getRareTreatManager().forceGiveTreat("TestPlayer", "spookey");
//
//        // Test command system
//        System.out.println("\n=== Testing Commands ===");
//        System.out.println("Testing /triggercandydrop:");
//        mod.getTriggerCandyDropCommand().executeCandyDropLogic("TestPlayer", false);
//
//        System.out.println("\nTesting forced candy drops:");
//        mod.getTriggerCandyDropCommand().forceGiveRareCandy("TestPlayer");
//        mod.getTriggerCandyDropCommand().forceGiveRegularCandy("TestPlayer");
//
//        System.out.println("Mod initialized successfully!");
//    }
}
