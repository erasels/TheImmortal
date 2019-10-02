package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.powers.BurningMadnessPower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class BurningMadness extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BurningMadness",
            0,
            CardType.POWER,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 2;
    private static final int STR_GAIN = 1;

    public BurningMadness() {
        super(cardInfo, true);
		
		baseMagicNumber2 = magicNumber2 = STR_GAIN;
        setMagic(MAGIC);
        setInnate(false, true);
        setRetain(true);
        tags.add(CardENUMs.HPLOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new BurningMadnessPower(p, magicNumber, magicNumber2));
    }
}