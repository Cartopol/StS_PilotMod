package pilot.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pilot.PilotMod;

public abstract class CustomPilotModPower extends AbstractPower implements CloneablePowerInterface, CustomStackBehaviorPower {
    protected final String[] DESCRIPTIONS;

    public int amount2 = -1;
    public boolean canGoNegative2 = false;
    protected Color redColor2 = Color.RED.cpy();
    protected Color greenColor2 = Color.GREEN.cpy();

    public CustomPilotModPower(StaticPowerInfo staticPowerInfo) {
        super();
        this.ID = staticPowerInfo.ID;
        this.name = staticPowerInfo.NAME;
        this.description = staticPowerInfo.DESCRIPTIONS[0];
        this.DESCRIPTIONS = staticPowerInfo.DESCRIPTIONS;
        this.region128 = staticPowerInfo.IMG_84;
        this.region48 = staticPowerInfo.IMG_32;
    }

    // Based on StSLib's TwoAmountPower
    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (this.amount2 > 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        } else if (this.amount2 < 0 && this.canGoNegative2) {
            this.redColor2.a = c.a;
            c = this.redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }

    @Override
    public void stackPower(AbstractPower otherPower) {
        PilotMod.logger.info("custom Stacking called");
        this.stackPower(otherPower.amount);

        if (this.amount2 != -1 || this.canGoNegative2 && otherPower instanceof CustomPilotModPower) {
            this.fontScale = 8.0F;
            this.amount2 += ((CustomPilotModPower) otherPower).amount2;
            PilotMod.logger.info("Amount2 stacked: {}", amount2);

        }
    }

    public void atStartOfTurnPreLoseBlock() { }
}

