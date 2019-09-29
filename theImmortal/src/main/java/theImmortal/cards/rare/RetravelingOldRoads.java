package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.utility.UpgradeMagicNumberAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.atb;

public class RetravelingOldRoads extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RetravelingOldRoads",
            1,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = -1;
    private static final int MAG = 3;
    private static final int MAG2 = 2;

    public RetravelingOldRoads() {
        super(cardInfo, false);

        setMagic(MAG);
        setCostUpgrade(VAL);
        baseMagicNumber2 = magicNumber2 = MAG2;
        shuffleBackIntoDrawPile = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryAction(magicNumber));
        atb(new UpgradeMagicNumberAction(this.uuid, magicNumber2));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(baseMagicNumber != MAG) {
            isMagicNumberModified = true;
        }
    }
}