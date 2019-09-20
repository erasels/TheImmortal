package theImmortal.cards.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static theImmortal.TheImmortal.makeID;

public class Blitz extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Blitz",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = -1;
    private static final int UPG_MAGIC = 2;
    private static final int HPCOST = 3;

    public Blitz() {
        super(cardInfo, true);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setHPCost(HPCOST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i<2;i++) {
            UC.doDmg(m, this.damage, MathUtils.randomBoolean() ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
        if(upgraded) {
            UC.atb(new DrawCardAction(UC.p, magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Blitz();
    }
}