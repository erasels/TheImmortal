package theImmortal.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.powers.LoseHPDrawCardPower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Caprice extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Caprice",
            2,
            CardType.POWER,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 2;
    private static final int COST = 1;

    public Caprice() {
        super(cardInfo, false);

        setCostUpgrade(COST);
        setMagic(VAL);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new LoseHPDrawCardPower(magicNumber));
    }
}