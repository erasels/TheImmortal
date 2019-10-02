package theImmortal.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theImmortal.actions.common.DoActionForAllCardsInHandAction;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class IndiscriminateIgnition extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IndiscriminateIgnition",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int HP = 5;

    public IndiscriminateIgnition() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setHPCost(HP);
        setMultiDamage(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DoActionForAllCardsInHandAction(list -> list.forEach(c -> {
            doAllDmg(damage, AbstractGameAction.AttackEffect.NONE, true);
            getAliveMonsters().forEach(mon -> att(new VFXAction(new InflameEffect(mon))));
            att(new VFXAction(new InflameEffect(p())));
        })));
        atb(new PressEndTurnButtonAction());
    }

    @Override
    public float getTitleFontSize() {
        return 17f;
    }
}