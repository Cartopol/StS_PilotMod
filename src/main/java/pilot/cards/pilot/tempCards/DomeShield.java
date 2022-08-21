package pilot.cards.pilot.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.actions.IncreaseShieldsAction;
import pilot.cards.pilot.titan_deck.CustomTitanCard;
import pilot.characters.Pilot;
import pilot.patches.ArmamentFieldPatch;
import pilot.powers.ProtectPower;


public class DomeShield extends CustomTitanCard{
    public static final String ID = PilotMod.makeID(DomeShield.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int PROTECT = 1;
    private static final int SHIELDS = 0;
    private static final int UPGRADE_SHIELDS = 5;

    public DomeShield() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        metaMagicNumber = baseMetaMagicNumber = SHIELDS;
        magicNumber = baseMagicNumber = PROTECT;
        ArmamentFieldPatch.isArmament.set(this, true);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, metaMagicNumber)));
        addToBot(new IncreaseShieldsAction((metaMagicNumber)));
    }

    @Override
    public void upgrade(){
        if(!upgraded){
            upgradeName();
            upgradeMetaMagicNumber(UPGRADE_SHIELDS);
            upgradeDescription();
        }
    }
}
