package si.nimbostratuz.bikeshare.services.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppProperties {

    @ConfigValue(value = "external-services.enabled", watch = true)
    private boolean externalServicesEnabled;
    @ConfigValue(value="bonus-for-high-rating", watch = true)
    private String bonusForHighRating;


    // Make bigDecimal from string and return it
    public BigDecimal getBonusForHighRatingNumber() {return new BigDecimal(bonusForHighRating);}
    public String getBonusForHighRating() {return bonusForHighRating;}

    public boolean isExternalServicesEnabled() {
        return externalServicesEnabled;
    }

    public void setExternalServicesEnabled(boolean externalServicesEnabled) {
        this.externalServicesEnabled = externalServicesEnabled;
    }

    public void setBonusForHighRating(String bonus) {
        this.bonusForHighRating = bonus;
    }
}