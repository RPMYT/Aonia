package net.lilydev.aonia.impl.spell;

import net.lilydev.aonia.api.spell.Spell;
import net.lilydev.aonia.api.spell.SpellDescription;
import net.lilydev.aonia.api.spell.SpellPiece;
import net.lilydev.aonia.impl.spell.util.AoniaSpellPieces;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MagicMissileSpell implements Spell {
    private final SpellDescription spell;
    public static final MagicMissileSpell INSTANCE = new MagicMissileSpell();

    private MagicMissileSpell() {
        SpellDescription.Builder builder = new SpellDescription.Builder(new Identifier("aonia", "magic_missile"), 0);
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

    @Override
    public ArrayList<SpellPiece> getPieces() {
        ArrayList<SpellPiece> list = new ArrayList<>();
        list.add(AoniaSpellPieces.HARM);
        return list;
    }
}
