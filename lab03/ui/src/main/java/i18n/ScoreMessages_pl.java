package i18n;

import java.util.ListResourceBundle;

public class ScoreMessages_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"general.score", "Zdobyłeś: {0} {1} z {2}"},
                {"score.limits", new double[]{0, 1, 2, 5}},
                {"score.formats", new String[]{"punktów", "punkt", "punkty", "punktów"}}
        };
    }
}
