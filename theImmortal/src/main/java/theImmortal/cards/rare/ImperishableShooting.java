package theImmortal.cards.rare;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.unique.ImperishableShootingAction;
import theImmortal.actions.utility.UpgradeMagicNumberAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;

import static theImmortal.TheImmortal.makeID;

public class ImperishableShooting extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ImperishableShooting",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int HIT_UPG = 2;

    public ImperishableShooting() {
        super(cardInfo, true);

        baseMagicNumber2 = HIT_UPG;
        setDamage(DAMAGE);
        setMagic(MAGIC);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(UC.p(), damage, DamageInfo.DamageType.NORMAL);
        UC.atb(new ImperishableShootingAction(UC.p().hb.cX, UC.p().hb.cY, magicNumber, info));
        UC.atb(new UpgradeMagicNumberAction(this.uuid, magicNumber2));
    }

    @Override
    public void applyPowers() {
        magicNumber2 = baseMagicNumber2;
        super.applyPowers();
        if(baseMagicNumber > MAGIC) {
            isMagicNumberModified = true;
        }
    }

    @Override
    public float getTitleFontSize() {
        return 17f;
    }
}