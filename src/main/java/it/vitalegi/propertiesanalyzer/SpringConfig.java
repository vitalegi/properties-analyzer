package it.vitalegi.propertiesanalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class SpringConfig {

	Logger log = LoggerFactory.getLogger(SpringConfig.class);

}
