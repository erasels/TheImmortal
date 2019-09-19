package theImmortal.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static theImmortal.TheImmortal.makeID;

public class Defend extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Defend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        tags.add(BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doDef(block);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Defend();
    }
}