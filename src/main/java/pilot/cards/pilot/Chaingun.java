package pilot.cards.pilot;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pilot.PilotMod;
import pilot.cards.CustomPilotModCard;
import pilot.patches.TitanFieldPatch;
import pilot.powers.LoseProtectPower;
import pilot.powers.ProtectPower;

public class Chaingun extends CustomPilotModCard {
    public static final String ID = PilotMod.makeID(Chaingun.class);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 6;
    private static final int PROTECT = 1;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Chaingun() {
        super(ID, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = PROTECT;
        TitanFieldPatch.requiresTitan.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(p, p, new ProtectPower(p, PROTECT)));
        addToBot(new ApplyPowerAction(p, p, new LoseProtectPower(p, PROTECT)));
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
