package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class EVA8 extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(EVA8.class);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;

    public EVA8() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (isEngaging()) {
            this.isMultiDamage = true;
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.FIRE));
        } else {
            this.isMultiDamage = false;
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.SLASH_HORIZONTAL));
        }
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
