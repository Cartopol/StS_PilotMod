package pilot.titan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pilot.PilotMod;

public class TitanOrb extends AbstractOrb {
    public static final Logger logger = LogManager.getLogger(PilotMod.class.getName());

    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.15F;
    private float vfxIntervalMax = 0.8F;
    private static final float PI_DIV_16 = 0.19634955F;
    private static final float ORB_WAVY_DIST = 0.05F;
    private static final float PI_4 = 12.566371F;
    private static final float ORB_BORDER_SCALE = 1.2F;
    private static final OrbStrings orbStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String ORB_ID = PilotMod.makeID("TitanOrb");
    public static Texture img;
    private int currentShields;
    private int baseShields;

    static {
        orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
        NAME = orbStrings.NAME;
        DESCRIPTIONS = orbStrings.DESCRIPTION;
    }

    public TitanOrb(int baseShields) {
        img = new Texture(PilotMod.makeOrbPath("TitanOrb.png"));
        this.baseEvokeAmount = 0;
        this.ID = PilotMod.makeID("TitanOrb");
        this.name = orbStrings.NAME;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        this.baseShields = baseShields;
        this.currentShields = this.baseShields;
        this.updateDescription();
    }

    public void damageTitan(float amount) {
        currentShields -= amount;
        logger.info("Reduced shields by {}, current shields: {}", amount, currentShields);
        if (currentShields <= 0) {
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
        }
    }

    public void updateDescription() {

    }

    public void onEvoke() {
//        AbstractDungeon.actionManager.addToBottom(new ChannelAction(this));
    }

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }

            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.currentShields), this.cX, this.cY + this.bobEffect.y / 2.0F - 50F, Color.LIGHT_GRAY.cpy(), this.fontScale);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }

    public AbstractOrb makeCopy() {
        return new TitanOrb(this.currentShields);
    }


}
