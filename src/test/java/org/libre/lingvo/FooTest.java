package org.libre.lingvo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by igorek2312 on 01.11.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FooTest {
    @Test
    public void test(){

    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date date=new Date();
       /* ZonedDateTime utcDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC)
                .atZone(ZoneOffset.UTC);*/
        ZonedDateTime utcDate = ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);

        ZonedDateTime kievDate = utcDate.withZoneSameInstant(ZoneId.of("Europe/Kiev"));

        String frm = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
                .format(kievDate);

        System.out.println(frm);
    }
}
