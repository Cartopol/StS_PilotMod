package pilot.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.runHistory.TinyCard;
import pilot.characters.Pilot;

public class TinyCardColorPatch {
    @SpirePatch(clz = TinyCard.class, method = "getIconBackgroundColor")
    public static class BackgroundColorPatch {
        @SpirePrefixPatch
        public static SpireReturn<Color> Prefix(TinyCard __this, AbstractCard card) {
            if (card.color == Pilot.Enums.PILOT_CARD_COLOR) {
                return SpireReturn.Return(Pilot.ColorInfo.CHARACTER_COLOR);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(clz = TinyCard.class, method = "getIconDescriptionColor")
    public static class DescriptionColorPatch {
        @SpirePrefixPatch
        public static SpireReturn<Color> Prefix(TinyCard __this, AbstractCard card) {
            if (card.color == Pilot.Enums.PILOT_CARD_COLOR) {
                return SpireReturn.Return(Color.GRAY.cpy());
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
