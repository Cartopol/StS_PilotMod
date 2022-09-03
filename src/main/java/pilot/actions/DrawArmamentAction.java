package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.cards.pilot.titan_deck.TitanDeck;
import pilot.cards.pilot.titan_deck.armaments.Rearm;

import static pilot.cards.pilot.titan_deck.TitanDeck.masterTitanDeck;

public class DrawArmamentAction extends AbstractGameAction {
    int number;

    public DrawArmamentAction( int number) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.number = number;
    }

    public DrawArmamentAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.number = 1;
    }

    public void update() {
        if (number > masterTitanDeck.size()) {
            for (AbstractCard c : masterTitanDeck.group) {
                AbstractDungeon.actionManager.addToBottom(new PileToHandAction(TitanDeck.masterTitanDeck, c, true));
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Rearm(), number - masterTitanDeck.size()));
        }
        else {
            for (int i = 0; i < number; ++i) {
                AbstractDungeon.actionManager.addToBottom(new TopOfPileToHandAction(TitanDeck.masterTitanDeck, true));
            }
            }
        this.isDone = true;
    }
}
