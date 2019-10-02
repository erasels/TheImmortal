package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.common.CallbackScryAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Foreordain extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Foreordain",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public Foreordain() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new CallbackScryAction(magicNumber, l-> l.forEach(c-> atb(new MakeTempCardInHandAction(c)))));
    }
}