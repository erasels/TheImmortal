package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theImmortal.actions.common.SwitchPilesAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Transpose extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Transpose",
            2,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 10;

    public Transpose() {
        super(cardInfo, true);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SwitchPilesAction());
        if(!upgraded) {
            doPow(p, new NoDrawPower(p));
            if(checkBurst()) {
                atb(new ScryAction(magicNumber));
            }
        } else {
            atb(new ScryAction(magicNumber));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(!upgraded) {
            tags.remove(CardENUMs.BURST);
        }
    }
}