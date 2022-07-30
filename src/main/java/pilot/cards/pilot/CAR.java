package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.characters.Pilot;

public class CAR extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(CAR.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pilot.Enums.PILOT_CARD_COLOR;

    private static final int COST = 0;
    private static final int DAMAGE = 1;
    private static final int UPGRADE_PLUS_DMG = 3;

    public CAR() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public boolean shouldGlowGold() {
        return ((Pilot)AbstractDungeon.player).hasMomentum;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (((Pilot)p).hasMomentum && p.hasPower(DexterityPower.POWER_ID)) {
            this.baseDamage += p.getPower(DexterityPower.POWER_ID).amount;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.SLASH_HORIZONTAL));
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
