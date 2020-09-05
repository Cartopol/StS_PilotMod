package shapes.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import shapes.cards.CustomShapesModCard;

import static shapes.ShapesMod.makeID;

// A second "magic" number for highlighting and display on cards, with possibly different upgraded values.
public class UrMagicNumber extends DynamicVariable {

    // Reference as "!shapes:UrMagic!" in card strings to actually display the number.
    @Override
    public String key() {
        return makeID("UrMagic");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((CustomShapesModCard) card).isUrMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((CustomShapesModCard) card).urMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((CustomShapesModCard) card).baseUrMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((CustomShapesModCard) card).upgradedUrMagicNumber;
    }
}
