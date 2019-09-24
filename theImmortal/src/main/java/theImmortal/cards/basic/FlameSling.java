package theImmortal.cards.basic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.powers.IgnitePower;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.unique.FlameSlingEffect;

import static theImmortal.TheImmortal.makeID;

public class FlameSling extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FlameSling",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 3;
    private static final int VULN = 1;
    private static final int DRAW = 2;

    public FlameSling() {
        super(cardInfo, true);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doVfx(new FlameSlingEffect(p.drawX, p.drawY+(p.hb_h/2f), m.drawX, m.drawY+(m.hb_h/2f)));
        UC.doPow(m, new IgnitePower(m, magicNumber));
        UC.doPow(m, new VulnerablePower(m, VULN, false));
        if(upgraded && UC.checkBurst()) {
            UC.atb(new DrawCardAction(UC.p, DRAW));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            tags.add(CardENUMs.BURST);
        }
        super.upgrade();
    }
}