package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.powers.IgnitePower;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class FireTailLash extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FireTailLash",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DMG = 7;
    private static final int MAG = 3;

    public FireTailLash() {
        super(cardInfo, true);

        setDamage(DMG);
        setMagic(MAG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        doVfx(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5f);
        doDmg(m, damage, AbstractGameAction.AttackEffect.NONE);
        if(upgraded) {
            doPow(m, new IgnitePower(m, damage));
        }
        doPow(m, new VulnerablePower(m, magicNumber, false));
    }
}