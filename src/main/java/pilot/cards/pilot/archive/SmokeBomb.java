package pilot.cards.pilot.archive;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.cards.pilot.tempCards.Electric_Smoke;
import pilot.characters.Pilot;
import pilot.patches.ReflexFieldPatch;

public class SmokeBomb extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(SmokeBomb.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int REFLEX_DAZED = 2;
    private static final int UPGRADED_PLUS_REFLEX_DAZED = 1;


    public SmokeBomb() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new Electric_Smoke();
        exhaust = true;
        selfRetain = true;
        magicNumber = baseMagicNumber = REFLEX_DAZED;
        ReflexFieldPatch.hasReflex.set(this, true);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (ReflexFieldPatch.hasReflex.get(this)) {
            if (((Pilot) AbstractDungeon.player).isReflexed()) {
                for (int i = 10; i > (AbstractDungeon.player).hand.size() + 1; --i) {
                    addToBot(new MakeTempCardInHandAction(new Electric_Smoke()));
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // count down from the hand limit to the player's hand size
        for (int i = 10; i > p.hand.size() - 1; --i) {
            addToBot(new MakeTempCardInHandAction(new Electric_Smoke()));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDescription();
            upgradeMagicNumber(UPGRADED_PLUS_REFLEX_DAZED);
            exhaust = false;
        }
    }
}
