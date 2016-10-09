package org.libre.lingvo.config.events;

import org.libre.lingvo.dao.VerificationTokenDao;
import org.libre.lingvo.entities.VerificationToken;
import org.libre.lingvo.tasks.DeleteNotEnabledUserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;
import java.util.Vector;

/**
 * Created by igorek2312 on 09.10.16.
 */

@Profile("dev")
@Component
public class StartupEmailVerificationChecker implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    @Lazy
    private Vector<DeleteNotEnabledUserTask> deleteNotEnabledUserTasks;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private Timer timer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<VerificationToken> list = verificationTokenDao.findAll();
        list.stream().forEach(
                token -> {
                    DeleteNotEnabledUserTask task = applicationContext.getBean(DeleteNotEnabledUserTask.class);
                    task.setTokenUuid(token.getId());
                    timer.schedule(task, token.getExpiryDate());
                    deleteNotEnabledUserTasks.add(task);
                }
        );
    }
}
