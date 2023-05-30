package ru.vtb.msa.rfrm.toggle;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("feature.toggle")
public class FeatureToggleProperty {

    private RoleModelToggle roleModelToggle;
}
