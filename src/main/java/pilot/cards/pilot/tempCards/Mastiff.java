package pilot.cards.pilot.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class Mastiff extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Mastiff.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADED_PLUS_DMG = 6;

    public Mastiff() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    @Override
    public boolean shouldGlowGold() {
        return ((Pilot) AbstractDungeon.player).hasAdvantage();
    }

    @Override
    protected int calculateBonusBaseDamage() {
        AbstractPlayer p = AbstractDungeon.player;
        int bonusDamage = 0;
        if (((Pilot)p).hasAdvantage()) {
            bonusDamage = baseDamage;
        }
        return bonusDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade() {
        if (!upgraded){
            upgradeName();
            upgradeDamage(UPGRADED_PLUS_DMG);
            upgradeDescription();
        }
    }
}
