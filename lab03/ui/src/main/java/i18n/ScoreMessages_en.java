package i18n;

import java.util.ListResourceBundle;

public class ScoreMessages_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"general.score", "You scored: {0} {1} out of {2}"},
                {"score.limits", new double[]{0, 1, 2}},
                {"score.formats", new String[]{"points", "point", "points"}}
        };
    }
}
