package pilot.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class AbstractCardMakeStatEquivalentCopyPatch {
    private final static String Name = AbstractCardMakeStatEquivalentCopyPatch.class.getSimpleName() + ": ";
    
    @SpirePostfixPatch
    // Copy every field we add to cards that lasts more than an action
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard __this) {

        if ((__result instanceof CustomPilotModCard) != (__this instanceof CustomPilotModCard)) {
            PilotMod.logger.error(Name + "source and copy aren't both PilotMod cards");
        } else if (__result instanceof CustomPilotModCard) {
            CustomPilotModCard card = (CustomPilotModCard) __result;
            CustomPilotModCard source = (CustomPilotModCard) __this;
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
