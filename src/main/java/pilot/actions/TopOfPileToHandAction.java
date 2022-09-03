package pilot.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.cards.OnDrawCardSubscriber;

public class TopOfPileToHandAction extends AbstractGameAction {
    private final CardGroup originalPile;
    private final boolean triggerDraw;

    public TopOfPileToHandAction(CardGroup originalPile, boolean triggerDraw) {
        this.originalPile = originalPile;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.triggerDraw = triggerDraw;
        }

    public void update() {
        if (originalPile.size() > 0 && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractCard card = originalPile.getTopCard();
            AbstractDungeon.player.hand.addToHand(originalPile.getTopCard());
            card.unhover();
            card.setAngle(0.0F, true);
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            originalPile.removeCard(card);

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();

            if (triggerDraw) {
                if (card instanceof OnDrawCardSubscriber) {
                    ((OnDrawCardSubscriber) card).onDraw();
                }
                if (AbstractDungeon.player instanceof OnDrawCardSubscriber) {
                    ((OnDrawCardSubscriber) AbstractDungeon.player).onDraw();
                }
                card.triggerWhenDrawn();
            }
        }
        this.isDone = true;
    }
}
