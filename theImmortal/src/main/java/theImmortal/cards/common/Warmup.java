package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static theImmortal.TheImmortal.makeID;

public class Warmup extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Warmup",
            0,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int HPLOSS = 3;

    public Warmup() {
        super(cardInfo, false);

        setHPCost(HPLOSS);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doVfx(new OfferingEffect());
        UC.atb(new DrawCardAction(magicNumber));
    }
}