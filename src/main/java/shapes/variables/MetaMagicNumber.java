package shapes.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import shapes.cards.CustomShapesModCard;

import static shapes.ShapesMod.makeID;

// A second "magic" number for highlighting and display on cards, with possibly different upgraded values.
public class MetaMagicNumber extends DynamicVariable {

    // Reference as "!shapes:MetaMagic!" in card strings to actually display the number.
    @Override
    public String key() {
        return makeID("MetaMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((CustomShapesModCard) card).isMetaMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((CustomShapesModCard) card).metaMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((CustomShapesModCard) card).baseMetaMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((CustomShapesModCard) card).upgradedMetaMagicNumber;
    }
}
