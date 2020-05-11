package the_warlord.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import the_warlord.WarlordMod;

public class PosturePower extends CustomWarlordModPower {
    public static final StaticPowerInfo STATIC = StaticPowerInfo.Load(PosturePower.class);
    public static final String POWER_ID = STATIC.ID;

    private boolean postureBroken;

    public PosturePower(AbstractCreature owner, int amount) {
        super(STATIC);

        this.type = PowerType.BUFF;

        this.owner = owner;
        this.amount = amount;

        this.postureBroken = false;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int damageTaken = damageAmount;
        if (info.type.equals(DamageInfo.DamageType.NORMAL)) {
            damageTaken = damageAmount - amount;
            //only trigger if damageAmount > 0
            if (damageTaken > 0) {
                flash();
                WarlordMod.logger.info("postureBroken: " + postureBroken);
                //only apply tension the first time Posture is broken in a turn
                if (!postureBroken) {
                    WarlordMod.logger.info("applying tension");

                    addToBot(new ApplyPowerAction(owner, owner, new TensionPower(owner, amount)));
                }
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
                this.postureBroken = true;
            }
        }
        return damageTaken < 0 ? 0 : damageTaken;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        this.postureBroken = false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PosturePower(owner, amount);
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);

        sb.setColor(c);
        float xPos = owner.dialogX;
        float yPos = owner.dialogY + 80;
        float xOffset = 200.0F;
        sb.draw(STATIC.TEXTURE_84, xPos + xOffset - 25, yPos - 12.0F, 16.0F, 16.0F, 50.0F, 50.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 84, 84, false, false);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        float xPos = owner.dialogX;
        float yPos = owner.dialogY;
        float xOffset = 200.0F;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.amount), xPos + xOffset, yPos, 0.7F, c);
    }
}