package org.libre.lingvo.config;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by igorek2312 on 11.10.16.
 */
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        ReloadableResourceBundleMessageSource.PropertiesHolder propertiesHolder = getMergedProperties(locale);
        Properties properties = propertiesHolder.getProperties();

        return properties;
    }
}
