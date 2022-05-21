package net.lilydev.aonia.impl.spell.util;

import net.lilydev.aonia.api.spell.SpellPiece;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AoniaSpellModifiers {
    public static final SpellPiece ADD_PROJECTILE_TRAIL = new SpellPiece(SpellPiece.Type.MODIFIER, new Identifier("aonia", "add_projectile_trail")) {
        @Override
        public void execute(ServerPlayerEntity caster) {
            super.execute(caster);
            this.data.putBoolean("HasTrail", true);
        }
    };

    public static final SpellPiece REMOVE_PROJECTILE_TRAIL = new SpellPiece(SpellPiece.Type.MODIFIER, new Identifier("aonia", "remove_projectile_trail")) {
        @Override
        public void execute(ServerPlayerEntity caster) {
            super.execute(caster);
            this.data.putBoolean("HasTrail", false);
        }
    };
}