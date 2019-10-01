package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.common.CallbackScryAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Preordained extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Preordained",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 3;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public Preordained() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new CallbackScryAction(magicNumber, list -> list.forEach(o -> doDmg(m, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true))));
    }
}