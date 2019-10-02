package theImmortal.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theImmortal.cards.abstracts.ImmortalCard;
import theImmortal.util.CardInfo;

import static theImmortal.TheImmortal.makeID;
import static theImmortal.util.UC.*;

public class FallingPhoenix extends ImmortalCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FallingPhoenix",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY);

    public final static String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 4;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public FallingPhoenix() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        if (CardCrawlGame.dungeon != null)
            configureCostsOnNewCard();
    }

    public void configureCostsOnNewCard() {
        updateCost(-getTurnBurstAmount());
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        int diff = this.cost - this.costForTurn;
        int tmpCost = cardInfo.cardCost - getTurnBurstAmount();
        if (tmpCost < 0) {
            tmpCost = 0;
        }

        if (tmpCost != this.cost) {
            this.isCostModified = true;
            this.cost = tmpCost;
            this.costForTurn = this.cost - diff;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        float yOffset = (m.hb_h/2.0f)*0.33f;
        float mtop = m.drawY+(m.hb_h/2.0f);
        for (int i = 0; i < magicNumber; i++) {
            float tmp = yOffset * i;
            doVfx(new FlashAtkImgEffect(m.drawX, mtop - tmp, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
            atb(new SFXAction("BLUNT_FAST"));
            doDmg(m, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE);
            //atb(new WaitAction(0.1f));
        }
    }
}