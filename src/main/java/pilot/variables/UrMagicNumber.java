package pilot.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import pilot.cards.CustomPilotModCard;

import static pilot.PilotMod.makeID;

// A second "magic" number for highlighting and display on cards, with possibly different upgraded values.
public class UrMagicNumber extends DynamicVariable {

    // Reference as "!pilot:UrMagic!" in card strings to actually display the number.
    @Override
    public String key() {
        return makeID("UrMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((CustomPilotModCard) card).isUrMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((CustomPilotModCard) card).urMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((CustomPilotModCard) card).baseUrMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((CustomPilotModCard) card).upgradedUrMagicNumber;
    }
}
