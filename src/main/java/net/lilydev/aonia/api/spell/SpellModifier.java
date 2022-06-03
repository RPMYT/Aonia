package net.lilydev.aonia.api.spell;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface SpellModifier {
    Identifier id();

    class Registry {
        private static final HashMap<Identifier, SpellModifier> SPELL_MODIFIERS = new HashMap<>();

        public static SpellModifier add(SpellModifier modifier) {
            SPELL_MODIFIERS.put(modifier.id(), modifier);
            return modifier;
        }

        public static SpellModifier get(Identifier id) {
            return SPELL_MODIFIERS.get(id);
        }
    }
}
