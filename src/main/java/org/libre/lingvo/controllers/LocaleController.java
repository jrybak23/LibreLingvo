package org.libre.lingvo.controllers;

import org.libre.lingvo.config.SerializableResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by igorek2312 on 12.10.16.
 */
@RestController
@RequestMapping("/messageBundle")
public class LocaleController {
    @Autowired
    private SerializableResourceBundleMessageSource messageBundle;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }
}
