package net.lilydev.aonia.api.spell;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public interface Spell {
    void execute(ServerPlayerEntity caster);
    int getRequiredCharge();
    ArrayList<SpellPiece> getPieces();
}