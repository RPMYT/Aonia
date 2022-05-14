package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellPiece;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AoniaSpellPieces {
    public static final SpellPiece HARM = SpellPiece.Registry.add(new SpellPiece(SpellPiece.Type.ACTION, new Identifier("aonia", "harm")) {
        @Override
        public void execute(ServerPlayerEntity caster) {
            super.execute(caster);
            this.targetedEntity().damage(DamageSource.MAGIC, 3F);
        }
    });
}
