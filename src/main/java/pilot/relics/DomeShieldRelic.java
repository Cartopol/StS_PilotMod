package pilot.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.armaments.Shield;
import pilot.characters.Pilot;

public class DomeShieldRelic extends CustomPilotModRelic  {
    public static final String ID = PilotMod.makeID(DomeShieldRelic.class);

    public DomeShieldRelic() {
        super(ID, Pilot.Enums.PILOT_CARD_COLOR, AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //replace this with the DomeShield card when it's implemented
        this.addToBot(new MakeTempCardInHandAction(new Shield(), 1, false));
    }
}
