package shapes.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static shapes.ShapesMod.makeID;

// For use with cards like "Deal !D! damage (!shapes:BaseDamage! + !M! damage per something)".
// Should generally only be used *in addition to* !D!, not *instead of* !D!.
public class BaseDamageNumber extends DynamicVariable {
    // Reference as "!shapes:BaseDamage!" in card strings to actually display the number.
    @Override
    public String key() {
        return makeID("BaseDamage");
    }

    @Override
    public boolean isModified(AbstractCard card) { return false; }

    @Override
    public int value(AbstractCard card) {
        return card.baseDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
