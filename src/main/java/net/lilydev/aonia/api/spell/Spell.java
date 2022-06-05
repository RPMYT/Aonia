package net.lilydev.aonia.api.spell;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;

public interface Spell {
    void execute(LivingEntity caster);
    int getRequiredCharge();
    ArrayList<SpellPiece> getPieces();
}