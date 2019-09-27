package theImmortal.enemies;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theImmortal.vfx.general.CalmFireEffect;
import theImmortal.vfx.general.CasualFlameParticleEffect;

public abstract class AbstractFlame extends CustomMonster {
    protected static final float HB_WIDTH = 140.0F; //scale is all multiplied in abstract monster class
    protected static final float HB_HEIGHT = 140.0F;

    protected static final float MAX_OFFSET = 20.0F * Settings.scale;
    protected static final float FLAME_CHANCE = 0.3f;

    protected float fireTimer = 0.0F;
    protected static final float FIRE_TIME = 0.025F;

    public FlameType ftype;

    protected String[] TipText;

    public enum FlameType {
        FULL, PURE, WILD
    }

    public AbstractFlame(float hb_x, float hb_y, String name, String ID, int maxHP) {
        super(name, ID, maxHP, hb_x, hb_y, HB_WIDTH, HB_HEIGHT, null);
    }

    @Override
    public void takeTurn() {
        //AbstractFlame don't do things.
    }

    @Override
    protected void getMove(int i) {
        setMove((byte) 0, AbstractMonster.Intent.NONE);
    }

    @Override
    public void flashIntent() {
    }

    @Override
    public void update() {
        super.update();
        if (!isDead && hb.hovered) {
            TipHelper.renderGenericTip(drawX + ((hb_w / 2.0f)), drawY + ((hb_h / 2.0f)), name, TipText[0]);
        }
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = FIRE_TIME;
                AbstractDungeon.effectList.add(new CalmFireEffect(hb.cX, hb.cY, getColor()));
                if (MathUtils.randomBoolean(FLAME_CHANCE)) {
                    float distance = MathUtils.random(MAX_OFFSET);
                    float direction = MathUtils.random(MathUtils.PI2);
                    float xOffset = MathUtils.cos(direction) * distance;
                    float yOffset = MathUtils.sin(direction) * distance;

                    AbstractDungeon.effectList.add(new CasualFlameParticleEffect(hb.cX + xOffset, hb.cY + yOffset));
                }
            }

            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && !(m instanceof AbstractFlame)) {
                    return;
                }
            }

            AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(this));
            AbstractDungeon.actionManager.addToTop(new SuicideAction(this, false));
        }
    }

    public abstract Color getColor();

    public abstract void deathAction();

    //Get's called by summon action if a flame of this type already exists
    public abstract void upgrade();

    @Override
    public void die() {
        super.die(false);
        deathAction();
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!this.isDead && !this.escaped) {
            this.hb.render(sb);
            //this.intentHb.render(sb);
            this.healthHb.render(sb);
        }

        if (!AbstractDungeon.player.isDead) {
            this.renderHealth(sb);
            this.renderName(sb);
        }
    }
}
