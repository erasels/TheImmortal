package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.common.CallbackScryAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Caution extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Caution",
            0,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 7;

    public Caution() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new CallbackScryAction(magicNumber, l -> l.forEach(c->doDef(block))));
    }
}