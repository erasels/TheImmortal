package theImmortal.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.actions.utility.DamageAllAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;
import theImmortal.util.UC;
import theImmortal.vfx.combat.BetterScreenOnFireEffect;

import static theImmortal.TheImmortal.makeID;

public class SpontaneousCombustion extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpontaneousCombustion",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int HPCOST = 7;
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 3;

    public SpontaneousCombustion() {
        super(cardInfo, false);

        setHPCost(HPCOST);
        AutoplayField.autoplay.set(this, true);
        setMultiDamage(true);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        UC.doVfx(new BetterScreenOnFireEffect(1.0f, 1.0f, "ATTACK_FLAME_BARRIER"));
        for(int i = 0; i < magicNumber; i ++) {
            UC.atb(new DamageAllAction(UC.p, damage, false, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, false));
        }
    }

    @Override
    public float getTitleFontSize() {
        return 15f;
    }
}