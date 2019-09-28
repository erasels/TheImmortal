package theImmortal.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.unique.SummonFlameAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.enemies.AbstractFlame;
import theImmortal.powers.MiragePower;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static theImmortal.TheImmortal.makeID;

public class Mirage extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Mirage",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 7;
    public static final int MAGIC = 1;

    public Mirage() {
        super(cardInfo, true);

        setBlock(BLOCK);
        setMagic(MAGIC);
        setBurst(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doDef(block);
        UC.atb(new SummonFlameAction(AbstractFlame.FlameType.FULL));

        if(upgraded && UC.checkBurst()) {
            UC.doPow(UC.p(), new MiragePower(magicNumber, null));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}