package pilot.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.armaments.Rearm;
import pilot.patches.ArmamentFieldPatch;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhumeArmamentAction extends AbstractGameAction {
    private AbstractPlayer p;
    private final boolean upgrade;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> exhumes = new ArrayList();

    public ExhumeArmamentAction(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator exhaustPile;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            ArrayList<AbstractCard> TitanCardsInExhaustPile = new ArrayList<>();
            for (AbstractCard d : AbstractDungeon.player.exhaustPile.group) {
                // remove all cards from the the exhaust pile that are not Armaments or Rearms
                if (ArmamentFieldPatch.isArmament.get(d) && !d.cardID.equals(Rearm.ID)) {
                    PilotMod.logger.info("Added {} to exhaust pile", d.name);
                    TitanCardsInExhaustPile.add(d);
                }
            }


            if (TitanCardsInExhaustPile.isEmpty()) {
                this.isDone = true;
            } else if (TitanCardsInExhaustPile.size() == 1) {

                c = TitanCardsInExhaustPile.get(0);
                c.unfadeOut();
                AbstractDungeon.actionManager.addToBottom(new ExhaustToTitanDeckAction(c));


                this.p.exhaustPile.removeCard(c);
                if (this.upgrade && c.canUpgrade()) {
                    c.upgrade();
                }

                c.unhover();
                c.fadingOut = false;
                this.isDone = true;
            } else {

                ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
                for (AbstractCard d : p.exhaustPile.group) {
                    d.stopGlowing();
                    d.unhover();
                    d.unfadeOut();
                    PilotMod.logger.info("Evaluated {} for Rearm", d.name);
                    if (!ArmamentFieldPatch.isArmament.get(d) || d.cardID.equals(Rearm.ID)) {
                        cardsToRemove.add(d);
                        this.exhumes.add(d);
                    }
                }
                for (AbstractCard e : cardsToRemove) {
                    p.exhaustPile.removeCard(e);
                    PilotMod.logger.info("Removed {} from exhaust pile because it is not an Armament.", e.name);
                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.exhumes);
                    this.exhumes.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
                    this.tickDuration();

                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (exhaustPile = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); exhaustPile.hasNext(); c.unhover()) {
                    c = (AbstractCard) exhaustPile.next();
//                    TitanDeck.masterTitanDeck.addToRandomSpot(c);
                    AbstractDungeon.actionManager.addToBottom(new ExhaustToTitanDeckAction(c));


                    this.p.exhaustPile.removeCard(c);
                    if (this.upgrade && c.canUpgrade()) {
                        c.upgrade();
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();

                for (exhaustPile = this.p.exhaustPile.group.iterator(); exhaustPile.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard) exhaustPile.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
