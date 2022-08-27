package pilot.cards.pilot.titan_deck.armaments;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import pilot.PilotMod;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.patches.ReflexFieldPatch;

public class PhaseDash extends CustomTitanCard {
    public static final String ID = PilotMod.makeID(PhaseDash.class);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int INTANGIBLE = 1;
    private static final int DISCARD = 3;
    private static final int UPGRADE_PLUS_DISCARD = -1;

    public PhaseDash() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = INTANGIBLE;
        metaMagicNumber = baseMetaMagicNumber = DISCARD;
        ReflexFieldPatch.hasReflex.set(this, true);
        this.exhaust = true;
        this.isEthereal = true;
        ArmamentFieldPatch.isArmament.set(this, true);
    }

    @Override
    public boolean shouldGlowGold() { return ((Pilot) AbstractDungeon.player).hasAdvantage();}

    public void triggerWhenDrawn(){
        super.triggerWhenDrawn();
        if (ReflexFieldPatch.hasReflex.get(this)) {
            if (((Pilot) AbstractDungeon.player).isReflexed()){
                addToBot(new ReduceCostAction(this.uuid, 1));
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       if (((Pilot)p).hasAdvantage()) {
           addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber)));
           addToBot(new DiscardAction(p, p, metaMagicNumber, false));
       }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_PLUS_DISCARD);
            upgradeDescription();
        }
    }
}
