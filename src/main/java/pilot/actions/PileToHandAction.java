package pilot.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import pilot.cards.OnDrawCardSubscriber;

public class PileToHandAction extends AbstractGameAction {
    private final CardGroup originalPile;
    private final AbstractCard card;
    private final boolean triggerDraw;


    public PileToHandAction(CardGroup originalPile, AbstractCard card, boolean triggerDraw) {
        this.originalPile = originalPile;
        this.triggerDraw = triggerDraw;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (originalPile.contains(this.card) && AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.hand.addToHand(this.card);
            this.card.unhover();
            this.card.setAngle(0.0F, true);
            this.card.lighten(false);
            this.card.drawScale = 0.12F;
            this.card.targetDrawScale = 0.75F;
            this.card.applyPowers();
            originalPile.removeCard(this.card);

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
