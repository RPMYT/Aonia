package net.lilydev.aonia.impl.spell;

import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.spell.util.AoniaSpellPieces;
import net.lilydev.aonia.impl.spell.util.AoniaSpellShapes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MagicMissileSpell {
    private SpellDescription spell;

    public static final MagicMissileSpell INSTANCE = new MagicMissileSpell();

    private MagicMissileSpell() {
        SpellDescription.Builder builder = new SpellDescription.Builder(AoniaSpellShapes.PROJECTILE.id, new Identifier("aonia", "magic_missile"));
        builder.addPiece(AoniaSpellPieces.HARM);
    }
}
