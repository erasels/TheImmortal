package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class FalseIgnition extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FalseIgnition",
            0,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 2;
    private static final int UPG_VAL = 1;

    public FalseIgnition() {
        super(cardInfo, true);
        setMagic(VAL, UPG_VAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ExhaustAction(1, !upgraded, false));
        doPow(p, new StrengthPower(p, magicNumber));
        doPow(p, new LoseStrengthPower(p, magicNumber));
    }
}