package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class RetreatingStrike extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RetreatingStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DMG = 6;
    private static final int UPG_DMG = 2;

    public RetreatingStrike() {
        super(cardInfo, false);

        setDamage(DMG, UPG_DMG);
        setBurst(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDmg(m, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        if (checkBurst()) {
            doDef(damage);
        }
    }
}