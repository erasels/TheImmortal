package theImmortal.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theImmortal.actions.common.CallbackScryAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Wandering extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Wandering",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public Wandering() {
        super(cardInfo, false);
        setBurst(false);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new CallbackScryAction(magicNumber, checkBurst()?cards -> doPow(p, new DrawCardNextTurnPower(p, cards.size())):null));
    }
}