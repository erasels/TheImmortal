package theImmortal.relics.abstracts;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import theImmortal.TheImmortal;
import theImmortal.util.TextureLoader;

public abstract class ImmortalRelic extends AbstractRelic {
    public ImmortalRelic(String setId, String imgName, RelicTier tier, LandingSound sfx) {
        super(setId, "", tier, sfx);

        imgUrl = imgName;

        if (img == null || outlineImg == null) {
            img = TextureLoader.getTexture(TheImmortal.makeRelicPath(imgName));
            largeImg = TextureLoader.getTexture(TheImmortal.makeRelicPath(imgName));
            outlineImg = TextureLoader.getTexture(TheImmortal.makeRelicOutlinePath(imgName));
        }
    }
}
