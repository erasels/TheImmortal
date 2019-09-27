package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.commons.lang3.math.NumberUtils;
import theImmortal.actions.utility.DamageAllAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.FireSplashEffect;

import static theImmortal.TheImmortal.makeID;

public class Flare extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Flare",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 4;

    public Flare() {
        super(cardInfo, true);
        baseMagicNumber2 = 0;

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = upgraded?magicNumber2+magicNumber:magicNumber2;
        UC.doVfx(new FireSplashEffect(p, NumberUtils.max(dmg*2, 10)));
        UC.atb(new DamageAllAction(p, dmg, false, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, false));
    }

    @Override
    public void applyPowers() {
        magicNumber2 = GameActionManager.damageReceivedThisCombat;
        super.applyPowers();
    }
}