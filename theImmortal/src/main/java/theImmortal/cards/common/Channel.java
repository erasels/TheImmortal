package theImmortal.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.unique.ChannelEffect;

import java.util.ArrayList;

import static theImmortal.TheImmortal.makeID;

public class Channel extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Channel",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int VAL = 4;
    private static final int UPG_VAL = 2;
    private static final int MAGIC = 2;

    public Channel() {
        super(cardInfo, false);

        isMultiDamage = true;
        setDamage(VAL, UPG_VAL);
        setMagic(MAGIC);

        setBurst(false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> mons = UC.getAliveMonster();
        int c = mons.size();

        mons.forEach(mon -> UC.doVfx(new ChannelEffect(mon.drawX, mon.drawY, p.drawX, p.drawY)));
        UC.doAllDmg(damage, AbstractGameAction.AttackEffect.NONE, false);

        if(UC.checkBurst()) {
            for (int i = 0; i < c; i++) {
                UC.atb(new HealAction(p, p, magicNumber));
            }
        }
    }
}