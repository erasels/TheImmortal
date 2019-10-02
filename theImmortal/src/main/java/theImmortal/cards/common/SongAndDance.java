package theImmortal.cards.common;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theImmortal.actions.common.HealForPowerAmountAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class SongAndDance extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SongAndDance",
            1,
            CardType.SKILL,
            CardTarget.SELF);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 8;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public SongAndDance() {
        super(cardInfo, true);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setBurst(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doPow(p, new StrengthPower(p, magicNumber));
        doPow(p, new LoseStrengthPower(p, magicNumber));
        if(!upgraded) {
            if(checkBurst()) {
                atb(new HealForPowerAmountAction(p, StrengthPower.POWER_ID));
            }
        } else {
            atb(new HealForPowerAmountAction(p, StrengthPower.POWER_ID));
            if(checkBurst()) {
                doPow(p, new NextTurnBlockPower(p, block));
            }
        }
    }
}