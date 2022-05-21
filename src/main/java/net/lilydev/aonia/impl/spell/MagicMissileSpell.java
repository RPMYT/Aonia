package net.lilydev.aonia.impl.spell;

import net.lilydev.aonia.api.spell.Spell;
import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.spell.util.AoniaSpellModifiers;
import net.lilydev.aonia.impl.spell.util.AoniaSpellPieces;
import net.lilydev.aonia.impl.spell.util.AoniaSpellShapes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MagicMissileSpell implements Spell {
    private final SpellDescription spell;
    public static final MagicMissileSpell INSTANCE = new MagicMissileSpell();

    private MagicMissileSpell() {
        ArrayList<Identifier> modifierList = new ArrayList<>();
        modifierList.add(AoniaSpellModifiers.ADD_PROJECTILE_TRAIL.id);
        Identifier[] modifiers = new Identifier[modifierList.size()];
        modifierList.toArray(modifiers);
        SpellDescription.Builder builder = new SpellDescription.Builder(AoniaSpellShapes.PROJECTILE.id, new Identifier("aonia", "magic_missile"), modifiers, 0);
        builder.addPiece(AoniaSpellPieces.HARM);
        this.spell = builder.build();
    }

    @Override
    public void execute(ServerPlayerEntity caster) {
        this.spell.execute(caster);
    }

    @Override
    public int getRequiredCharge() {
        return 0;
    }
}
