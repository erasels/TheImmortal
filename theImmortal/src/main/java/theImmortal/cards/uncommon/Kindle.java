package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class Kindle extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Kindle",
            1,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 2;
    private static final int UPG_VAL = 1;
    private static final int HPLOSS = 6;
    private static final int HEAL = 4;

    public Kindle() {
        super(cardInfo, false);

        setHPCost(HPLOSS);
        setMagic(VAL, UPG_VAL);
        baseMagicNumber2 = HEAL;
        tags.add(CardENUMs.BURST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new InflameEffect(p));
        doPow(p, new StrengthPower(p, magicNumber));

        if(checkBurst()) {
            atb(new HealAction(p, p, magicNumber2));
        }
    }

    @Override
    public void applyPowers() {
        magicNumber2 = HEAL;
        super.applyPowers();
    }
}