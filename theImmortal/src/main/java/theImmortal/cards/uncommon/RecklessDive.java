package theImmortal.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import java.util.concurrent.atomic.AtomicInteger;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class RecklessDive extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RecklessDive",
            0,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int ATK_AMT = 3;
    private static final int HPCOST = 3;
    private static final int UPG_HPCOST = -4;


    public RecklessDive() {
        super(cardInfo, false);

        setMagic(ATK_AMT);
        setHPCost(HPCOST, UPG_HPCOST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_PIERCING_WAIL"));
        doVfx(new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.FIREBRICK, ShockWaveEffect.ShockWaveType.CHAOTIC));
        AtomicInteger combiCost = new AtomicInteger();
        atb(new FetchAction(p.drawPile,
                c -> c.type == CardType.ATTACK,
                magicNumber,
                ac -> {
                    for (AbstractCard c : ac) {
                        if (c.costForTurn > 0) {
                            combiCost.addAndGet(c.costForTurn);
                        } else if (c.costForTurn == -1) {
                            combiCost.addAndGet(AbstractDungeon.player.energy.energy);
                        }
                    }
                    if(combiCost.get() > 0) {
                        att(new LoseHPAction(p, p, combiCost.get()));
                    }
                    AbstractDungeon.player.hand.refreshHandLayout();
                }));
    }
}