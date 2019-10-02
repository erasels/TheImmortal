package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.powers.CursedPower;
import theImmortal.powers.IgnitePower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class CursedSign extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CursedSign",
            1,
            CardType.SKILL,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 5;
    private static final int VULN = 2;
    private static final int UPG_COST = 0;

    public CursedSign() {
        super(cardInfo, false);

        baseMagicNumber2 = magicNumber2 = VULN;
        setCostUpgrade(UPG_COST);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(m, new IgnitePower(m, magicNumber));
        doPow(m, new VulnerablePower(m, magicNumber2, false));
        doPow(m,new CursedPower(m));
    }
}