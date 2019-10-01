package theImmortal.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theImmortal.util.UC;

public class RecklessSacrificeAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int energyOnUse;
    private int damage;
    private int hploss;

    public RecklessSacrificeAction(AbstractMonster m, int damage, int hploss, boolean freeToPlayOnce, int energyOnUse) {
        this.freeToPlayOnce = false;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.target = m;
        this.damage = damage;
        this.hploss = hploss;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (UC.p().hasRelic(ChemicalX.ID)) {
            effect += 2;
            UC.p().getRelic(ChemicalX.ID).flash();
        }
        int dmg, hpl;
        if (effect > 0) {
            dmg = damage;
            hpl = hploss;
            for (int i = 0; i < effect; i++) {
                UC.atb(new LoseHPIfMonsterNotDeadAction(UC.p(), UC.p(), hpl, (AbstractMonster)target));
                UC.doDmg(target, dmg);

                hpl*=2;
                dmg*=2;
            }
            if (!this.freeToPlayOnce)
                UC.p().energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
