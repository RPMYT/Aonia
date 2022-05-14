package net.lilydev.aonia.impl.spell;

import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.impl.spell.util.AoniaSpellShapes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MagicMissileSpell extends SpellDescription {
    public static final MagicMissileSpell INSTANCE = new MagicMissileSpell();

    private MagicMissileSpell() {
        super(AoniaSpellShapes.PROJECTILE.id(), new Identifier("aonia", "magic_missile"));
        this.markFinished();
    }

    @Override
    public void execute(ServerPlayerEntity caster) {
        super.execute(caster);
    }


}
