package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellModifier;
import net.minecraft.util.Identifier;

public enum AoniaSpellModifiers implements SpellModifier {
    SNIPING(new Identifier("aonia", "sniping")),
    INVISIBLE(new Identifier("aonia", "invisible")),
    ANGELIC(new Identifier("aonia", "angelic"))

    ;
    final Identifier id;

    AoniaSpellModifiers(Identifier id) {
        this.id = id;
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}