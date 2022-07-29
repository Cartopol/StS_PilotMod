package pilot.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static pilot.PilotMod.makeID;

// For use with cards like "Deal !D! damage (!pilot:BaseDamage! + !M! damage per something)".
// Should generally only be used *in addition to* !D!, not *instead of* !D!.
public class BaseDamageNumber extends DynamicVariable {
    // Reference as "!pilot:BaseDamage!" in card strings to actually display the number.
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
