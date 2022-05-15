package net.lilydev.aonia.api.spell;

import net.minecraft.server.network.ServerPlayerEntity;

public interface Spell {
    void execute(ServerPlayerEntity caster);
    int getRequiredCharge();
}
