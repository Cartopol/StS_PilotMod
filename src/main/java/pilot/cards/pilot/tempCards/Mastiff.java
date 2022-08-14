package pilot.cards.pilot.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.MomentumPower;

public class Mastiff extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Mastiff.class);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADED_PLUS_DMG = 5;
    private static final int MOMENTUM = 2;

    public Mastiff() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MOMENTUM;
        this.exhaust = true;
    }

    @Override
    public boolean shouldGlowGold() {
        return ((Pilot) AbstractDungeon.player).hasAdvantage();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, magicNumber)));

        if (((Pilot)p).hasAdvantage()) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
        }
    }

    public void upgrade() {
        if (!upgraded){
            upgradeDamage(UPGRADED_PLUS_DMG);
        }
    }
}
