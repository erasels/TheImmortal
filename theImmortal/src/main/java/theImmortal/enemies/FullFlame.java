package theImmortal.enemies;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import theImmortal.actions.utility.DamageAllAction;
import theImmortal.util.UC;
import theImmortal.vfx.combat.FireSplashEffect;

import static theImmortal.TheImmortal.makeID;

public class FullFlame extends AbstractFlame {
    private static final int MHP = 20;
    public static final String ID = makeID("FullFlame");

    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    public FullFlame(float hb_x, float hb_y) {
        super(hb_x, hb_y, monsterStrings.NAME, ID, MHP);
        TipText = monsterStrings.DIALOG;
        ftype = FlameType.FULL;
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public void deathAction() {
        UC.doVfx(new ExplosionSmallEffect(drawX, drawY));
        UC.atb(new DamageAllAction(this, this.maxHealth, true, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, false));
    }

    @Override
    public void upgrade() {
        UC.doVfx(new FireSplashEffect(this, MHP));
        increaseMaxHp(MHP/2, false);
    }
}