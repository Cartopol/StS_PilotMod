package shapes.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import shapes.ShapesMod;
import shapes.cards.CustomShapesModCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class AbstractCardMakeStatEquivalentCopyPatch {
    private final static String Name = AbstractCardMakeStatEquivalentCopyPatch.class.getSimpleName() + ": ";
    
    @SpirePostfixPatch
    // Copy every field we add to cards that lasts more than an action
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard __this) {

        if ((__result instanceof CustomShapesModCard) != (__this instanceof CustomShapesModCard)) {
            ShapesMod.logger.error(Name + "source and copy aren't both ShapesMod cards");
        } else if (__result instanceof CustomShapesModCard) {
            CustomShapesModCard card = (CustomShapesModCard) __result;
            CustomShapesModCard source = (CustomShapesModCard) __this;
            card.urMagicNumber = source.urMagicNumber;
            card.baseUrMagicNumber = source.baseUrMagicNumber;
            card.isUrMagicNumberModified = source.isUrMagicNumberModified;
            card.upgradedUrMagicNumber = source.upgradedUrMagicNumber;
            card.metaMagicNumber = source.metaMagicNumber;
            card.baseMetaMagicNumber = source.baseMetaMagicNumber;
            card.isMetaMagicNumberModified = source.isMetaMagicNumberModified;
            card.upgradedMetaMagicNumber = source.upgradedMetaMagicNumber;
        }
        
        return __result;
    }
}
