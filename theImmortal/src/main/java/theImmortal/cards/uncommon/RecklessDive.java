package theImmortal.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.patches.cards.CardENUMs;
import theImmortal.util.CardInfo;

import java.util.concurrent.atomic.AtomicInteger;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.atb;
import static theImmortal.util.UC.doVfx;

public class RecklessDive extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Expiation",
            0,
            CardType.SKILL,
            CardTarget.SELF
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;
    private static final int ATK_AMT = 3;

    public RecklessDive() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(ATK_AMT);
        tags.add(CardENUMs.HPLOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_PIERCING_WAIL"));
        doVfx(new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F);
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
                    AbstractDungeon.player.hand.refreshHandLayout();
                }));
        atb(new LoseHPAction(p, p, combiCost.get()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Expiation();
    }
}