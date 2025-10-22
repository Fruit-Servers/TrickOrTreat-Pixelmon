package com.halloween.trickortreat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    
    private final File configFile;
    private JsonObject config;
    private final Gson gson;
    
    public ConfigManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        // For now, use current directory. In real mod, this would be config directory
        this.configFile = new File("trickortreat-pixelmon-config.json");
        loadConfig();
    }
    
    private void loadConfig() {
        if (!configFile.exists()) {
            createDefaultConfig();
        }
        
        try (FileReader reader = new FileReader(configFile)) {
            config = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println("🎃 Loaded configuration from: " + configFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            createDefaultConfig();
        }
    }
    
    private void createDefaultConfig() {
        config = new JsonObject();
        
        // Candy drop settings
        JsonObject candyDrop = new JsonObject();
        candyDrop.addProperty("enabled", true);
        candyDrop.addProperty("drop-chance", 15.0);
        candyDrop.addProperty("drop-from-wild", true);
        candyDrop.addProperty("drop-from-legendary", true);
        candyDrop.addProperty("drop-from-mythical", true);
        candyDrop.addProperty("drop-from-ultra-beast", true);
        config.add("candy-drop", candyDrop);
        
        // Rare treats
        JsonObject rareTreats = new JsonObject();
        rareTreats.addProperty("treat-chance", 50.0);
        
        JsonObject individualChances = new JsonObject();
        individualChances.addProperty("token", 10);
        individualChances.addProperty("collectpass", 10);
        individualChances.addProperty("fruitkey", 20);
        individualChances.addProperty("spookey", 20);
        individualChances.addProperty("netherite", 10);
        individualChances.addProperty("wspawn", 5);
        individualChances.addProperty("sspawn", 15);
        rareTreats.add("individual-chances", individualChances);
        
        config.add("rare-treats", rareTreats);
        
        // Cooldowns
        JsonObject cooldowns = new JsonObject();
        cooldowns.addProperty("rare-candy-hours", 1);
        cooldowns.addProperty("candy-use-seconds", 3);
        config.add("cooldowns", cooldowns);
        
        // Messages
        JsonObject messages = new JsonObject();
        messages.addProperty("prefix", "&6[&c🎃&6] &r");
        messages.addProperty("candy-received", "&aYou found Halloween Candy! Right-click to use it!");
        messages.addProperty("rare-candy-received", "&d&lRARE! &r&bYou found Rare Halloween Candy! Right-click to use it!");
        messages.addProperty("treat-received", "&a&lTREAT! &r&a{treat}");
        messages.addProperty("trick-received", "&c&lTRICK! &r&c{trick}");
        messages.addProperty("rare-treat-received", "&d&lRARE TREAT! &r&d{treat}");
        messages.addProperty("rare-trick-received", "&5&lRARE TRICK! &r&5{trick}");
        messages.addProperty("cooldown-active", "&c⏰ You must wait {time} before receiving another rare candy!");
        messages.addProperty("candy-use-cooldown", "&c⏰ You must wait {time} seconds before using another candy!");
        config.add("messages", messages);
        
        saveConfig();
        System.out.println("🎃 Created default configuration at: " + configFile.getAbsolutePath());
    }
    
    private void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }
    
    // Candy drop settings
    public boolean isCandyDropEnabled() {
        return config.getAsJsonObject("candy-drop").get("enabled").getAsBoolean();
    }
    
    public double getCandyDropChance() {
        return config.getAsJsonObject("candy-drop").get("drop-chance").getAsDouble();
    }
    
    public boolean canDropFromWild() {
        return config.getAsJsonObject("candy-drop").get("drop-from-wild").getAsBoolean();
    }
    
    public boolean canDropFromLegendary() {
        return config.getAsJsonObject("candy-drop").get("drop-from-legendary").getAsBoolean();
    }
    
    public boolean canDropFromMythical() {
        return config.getAsJsonObject("candy-drop").get("drop-from-mythical").getAsBoolean();
    }
    
    public boolean canDropFromUltraBeast() {
        return config.getAsJsonObject("candy-drop").get("drop-from-ultra-beast").getAsBoolean();
    }
    
    // Rare treat settings
    public double getRareTreatChance() {
        return config.getAsJsonObject("rare-treats").get("treat-chance").getAsDouble();
    }
    
    public Map<String, Integer> getRareTreatChances() {
        Map<String, Integer> chances = new HashMap<>();
        JsonObject individualChances = config.getAsJsonObject("rare-treats").getAsJsonObject("individual-chances");
        
        for (String key : individualChances.keySet()) {
            chances.put(key, individualChances.get(key).getAsInt());
        }
        
        return chances;
    }
    
    // Cooldown settings
    public long getRareCandyCooldownMillis() {
        int hours = config.getAsJsonObject("cooldowns").get("rare-candy-hours").getAsInt();
        return hours * 60 * 60 * 1000L;
    }
    
    public long getCandyUseCooldownMillis() {
        int seconds = config.getAsJsonObject("cooldowns").get("candy-use-seconds").getAsInt();
        return seconds * 1000L;
    }
    
    // Messages
    public String getMessage(String key) {
        JsonObject messages = config.getAsJsonObject("messages");
        if (messages.has(key)) {
            return messages.get(key).getAsString().replace("&", "§");
        }
        return "Message not found: " + key;
    }
    
    public String getFormattedMessage(String key, String... replacements) {
        String message = getMessage(key);
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }
        return message;
    }
}
