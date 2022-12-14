package pilot.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import pilot.cards.CustomPilotModCard;

public class LoadMiscValuePatch {
    @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
    public static class CardLibrary_getCopyPatch {
        @SpirePostfixPatch
        public static AbstractCard patch(AbstractCard __result, String key, int upgradeTime, int misc) {
            if(misc != 0 && __result instanceof CustomPilotModCard) {
                ((CustomPilotModCard) __result).applyLoadedMiscValue(misc);
            }
            return __result;
        }
    }
}
