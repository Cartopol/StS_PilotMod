package pilot.cards.pilot.titan_deck;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.actions.PileToHandAction;
import pilot.cards.OnDrawCardSubscriber;
import pilot.cards.pilot.titan_deck.armaments.Rearm;

import java.util.ArrayList;

public class TitanDeck {
    public static int playedThisCombatCount = 0;

    public static final CardGroup masterTitanDeck = new CardGroup(CardGroupType.UNSPECIFIED);
    public static final ArrayList<AbstractCard> titanOptions = new ArrayList<>();

    public static void reset() {
        masterTitanDeck.clear();
        playedThisCombatCount = 0;
    }

    public static void drawArmament(boolean leaveCopyInTitanDeck) {
        if (masterTitanDeck.size() > 0) {
            // Draw the top card of the masterTitanDeck
            AbstractCard c = masterTitanDeck.getTopCard();

            if (leaveCopyInTitanDeck) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(masterTitanDeck.getTopCard()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new PileToHandAction(TitanDeck.masterTitanDeck, masterTitanDeck.getTopCard()));
            }
            if (c instanceof OnDrawCardSubscriber) {
                ((OnDrawCardSubscriber) c).onDraw();
            }
            if (AbstractDungeon.player instanceof OnDrawCardSubscriber) {
                ((OnDrawCardSubscriber)AbstractDungeon.player).onDraw();
            }
            c.triggerWhenDrawn();


        }
        else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Rearm()));
        }
    }

    public void moveToTitanDeck(AbstractCard c, boolean randomSpot) {
        c.shrink();
        AbstractDungeon.getCurrRoom().souls.onToDeck(c, randomSpot);
    }

//    public static ArrayList<AbstractCard> getTitanOptions() {
//        titanOptions.clear();
//        int nbOptions = 3;
//        if (masterTitanDeck.size() < nbOptions) {
//            nbOptions = masterTitanDeck.size();
//        }
//        masterTitanDeck.shuffle();
//        for (int i = 0; i < nbOptions; ++i) {
//            AbstractCard c = masterTitanDeck.getNCardFromTop(i);
//            c.applyPowers();
//            titanOptions.add(c.makeSameInstanceOf());
//        }
//
//        return titanOptions;
//    }
}
