package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.powers.UpdraftPower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Updraft extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Updraft",
            1,
            CardType.POWER,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 2;
    private static final int UPG_VAL = 1;

    public Updraft() {
        super(cardInfo, false);
        setMagic(VAL, UPG_VAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new UpdraftPower(magicNumber));
    }
}