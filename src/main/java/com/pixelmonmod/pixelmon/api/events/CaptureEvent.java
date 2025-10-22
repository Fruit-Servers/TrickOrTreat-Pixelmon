package com.pixelmonmod.pixelmon.api.events;

import net.minecraftforge.eventbus.api.Event;

// Stub class for compilation - will be replaced by actual Pixelmon at runtime
public class CaptureEvent extends Event {
    
    public static class SuccessfulCapture extends CaptureEvent {
        private Object player;
        private PokemonWrapper pokemonWrapper;
        
        public SuccessfulCapture(Object player, PokemonWrapper pokemonWrapper) {
            this.player = player;
            this.pokemonWrapper = pokemonWrapper;
        }
        
        public Object getPlayer() { return player; }
        public PokemonWrapper getPokemon() { return pokemonWrapper; }
    }
    
    public static class PokemonWrapper {
        public BeatWildPixelmonEvent.Pokemon pokemon;
        
        public PokemonWrapper(BeatWildPixelmonEvent.Pokemon pokemon) {
            this.pokemon = pokemon;
        }
    }
}
