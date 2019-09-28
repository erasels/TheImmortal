package theImmortal.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.FlameBurstEffect;

import static theImmortal.TheImmortal.makeID;

public class Scorch extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Scorch",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int HIT_COUNT = 2;
    private static final int COST_UPG = 1;

    public Scorch() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(HIT_COUNT);
        setCostUpgrade(COST_UPG);

        setBurst(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            UC.doDmg(m, damage, i%2==0? AbstractGameAction.AttackEffect.SLASH_VERTICAL: AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }
        if (m != null) {
            if (UC.checkBurst()) {
                UC.doVfx(new FlameBurstEffect(m.hb.cX, m.hb.cY, 30));
                UC.doDmg(m, damage, AbstractGameAction.AttackEffect.FIRE);
            }
        }
    }
}