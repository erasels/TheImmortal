package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.powers.RecklessAttackPower;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static theImmortal.TheImmortal.makeID;

public class RecklessAttack extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RecklessAttack",
            0,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int HPCOST = 3;
    private static final int UPG_HPCOST = -1;


    public RecklessAttack() {
        super(cardInfo, false);

        setMagic(HPCOST, UPG_HPCOST);
        tags.add(CardENUMs.HPLOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doPow(p, new RecklessAttackPower(magicNumber));
    }
}