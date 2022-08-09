package pilot.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import pilot.cards.pilot.titan_deck.TitanDeck;
import pilot.characters.Pilot;

import java.util.Iterator;

public class ShowCardAndAddToTitanDeckEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;
    private AbstractCard card;
    private static final float PADDING;
    private boolean randomSpot;
    private boolean cardOffset;

    public ShowCardAndAddToTitanDeckEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset, boolean toBottom) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.cardOffset = cardOffset;
        this.duration = 1.5F;
        this.randomSpot = randomSpot;
        if (cardOffset) {
            this.identifySpawnLocation(x, y);
        } else {
            this.card.target_x = x;
            this.card.target_y = y;
        }

        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        if (this.card.type != CardType.CURSE && this.card.type != CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.card.upgrade();
        }

        CardCrawlGame.sound.play("CARD_OBTAIN");
        if (AbstractDungeon.player instanceof Pilot) {
            if (toBottom) {
                TitanDeck.masterTitanDeck.addToBottom(this.card);
            } else if (randomSpot) {
                TitanDeck.masterTitanDeck.addToRandomSpot(this.card);
            } else {
                TitanDeck.masterTitanDeck.addToTop(this.card);
            }
        }

    }

    public ShowCardAndAddToTitanDeckEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset) {
        this(srcCard, x, y, randomSpot, cardOffset, false);
    }

    public ShowCardAndAddToTitanDeckEffect(AbstractCard srcCard, float x, float y, boolean randomSpot) {
        this(srcCard, x, y, randomSpot, false);
    }

    public ShowCardAndAddToTitanDeckEffect(AbstractCard srcCard, boolean randomSpot, boolean toBottom) {
        this.randomSpot = false;
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5F;
        this.randomSpot = randomSpot;
        this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.8F, (float)Settings.HEIGHT * 0.2F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        if (AbstractDungeon.player instanceof Pilot) {
            if (toBottom) {
                TitanDeck.masterTitanDeck.addToBottom(srcCard);
            } else if (randomSpot) {
                TitanDeck.masterTitanDeck.addToRandomSpot(srcCard);
            } else {
                TitanDeck.masterTitanDeck.addToTop(srcCard);
            }
        }

    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        if (this.cardOffset) {
            effectCount = 1;
        }

        Iterator var4 = AbstractDungeon.effectList.iterator();

        while(var4.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var4.next();
            if (e instanceof ShowCardAndAddToTitanDeckEffect) {
                ++effectCount;
            }
        }

        this.card.current_x = x;
        this.card.current_y = y;
        this.card.target_y = (float)Settings.HEIGHT * 0.5F;
        switch(effectCount) {
            case 0:
                this.card.target_x = (float)Settings.WIDTH * 0.5F;
                break;
            case 1:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                break;
            case 2:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                break;
            case 3:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            case 4:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            default:
                this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
            AbstractDungeon.getCurrRoom().souls.onToDeck(this.card, this.randomSpot, true);
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
