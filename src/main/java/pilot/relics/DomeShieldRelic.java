package pilot.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import pilot.PilotMod;
import pilot.cards.pilot.tempCards.DomeShield;
import pilot.util.TextureLoader;

public abstract class DomeShieldRelic extends CustomRelic {
    public static final String ID = "DomeShieldRelic";
    public final CardColor relicColor;

    private static Texture relicTextureFromId(String id) {
        String unprefixedId = id.replace(PilotMod.MOD_ID + ":","");
        String path = String.format("%1$sResources/images/relics/%2$s.png", PilotMod.MOD_ID, unprefixedId);
        return TextureLoader.getTexture(path);
    }

    private static Texture relicOutlineTextureFromId(String id) {
        String unprefixedId = id.replace(PilotMod.MOD_ID + ":","");
        String path = String.format("%1$sResources/images/relics/generated/%2$s_outline.png", PilotMod.MOD_ID, unprefixedId);
        return TextureLoader.getTexture(path);
    }

    public DomeShieldRelic(final String id, final CardColor relicColor, final RelicTier relicTier, final LandingSound landingSound) {
        super(id, relicTextureFromId(id), relicOutlineTextureFromId(id), relicTier, landingSound);
        this.relicColor = relicColor;
    }


    @Override
    public void initializeTips() {
        this.description = DESCRIPTIONS[0];
        super.initializeTips();
        this.description = getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0].replaceAll(PilotMod.MOD_ID + ":", "#y");
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.addToBot(new MakeTempCardInHandAction(new DomeShield(), 1, false));
    }
}