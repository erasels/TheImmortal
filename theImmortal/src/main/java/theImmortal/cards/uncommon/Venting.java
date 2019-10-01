package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.common.CallbackDrawAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Venting extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Venting",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Venting() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doDef(block);
        atb(new CallbackDrawAction(magicNumber, c -> {
            int i = getLogicalCardCost(c);
            if(i > 0)
                p().heal(i);
        }));
    }


}