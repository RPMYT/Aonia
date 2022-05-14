package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellPiece;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class AoniaSpellPieces {
    public static final SpellPiece HARM = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.ACTION, new Identifier("aonia", "harm")) {
        @Override
        public void execute(ServerPlayerEntity caster, ArrayList<Identifier> modifiers) {
            super.execute(caster, modifiers);
            this.targetedEntity().damage(DamageSource.MAGIC, 3.14F);
        }
    });
}
