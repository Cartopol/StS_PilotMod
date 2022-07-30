//package pilot.patches;
//
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
//import com.megacrit.cardcrawl.actions.common.DrawCardAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//import pilot.characters.Pilot;
//
//public class AdvantagePatch {
//    @SpirePatch(
//            clz = AbstractCard.class,
//            method = "triggerWhenDrawn"
//    )
//    public static class AbstractCard_triggerWhenDrawn {
//        @SpirePrefixPatch
//        public static void patch(AbstractCard __this) {
//
//            ((Pilot)AbstractDungeon.player).hasAdvantage = true;
//        }
//    }
//}
