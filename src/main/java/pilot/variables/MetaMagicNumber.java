package pilot.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import pilot.cards.CustomPilotModCard;

import static pilot.PilotMod.makeID;

// A second "magic" number for highlighting and display on cards, with possibly different upgraded values.
public class MetaMagicNumber extends DynamicVariable {

    // Reference as "!pilot:MetaMagic!" in card strings to actually display the number.
    @Override
    public String key() {
        return makeID("MetaMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((CustomPilotModCard) card).isMetaMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((CustomPilotModCard) card).metaMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((CustomPilotModCard) card).baseMetaMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((CustomPilotModCard) card).upgradedMetaMagicNumber;
    }
}
