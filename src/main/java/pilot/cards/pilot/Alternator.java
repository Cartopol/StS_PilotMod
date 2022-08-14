package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;
import pilot.powers.MomentumPower;

public class Alternator extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Alternator.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int DAMAGE_PER_MOMENTUM = 1;

    public Alternator() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DAMAGE_PER_MOMENTUM;
    }

    @Override
    public boolean shouldGlowGold() {
        return ((Pilot)AbstractDungeon.player).hasMomentum();
    }

    @Override
    protected int calculateBonusBaseDamage() {
        AbstractPlayer p =AbstractDungeon.player;
        int bonusDamage = 0;
        if (p.hasPower(MomentumPower.POWER_ID)) {
            bonusDamage = p.getPower(MomentumPower.POWER_ID).amount * magicNumber;
        }
        return bonusDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (((Pilot)p).hasMomentum() && p.hasPower(MomentumPower.POWER_ID)) {
            this.baseDamage += p.getPower(MomentumPower.POWER_ID).amount;
        }
        this.addToBot(new AttackDamageRandomEnemyAction(this, AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new AttackDamageRandomEnemyAction(this, AttackEffect.SLASH_HORIZONTAL));
        this.baseDamage -= p.getPower(MomentumPower.POWER_ID).amount;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeDescription();
        }
    }
}
