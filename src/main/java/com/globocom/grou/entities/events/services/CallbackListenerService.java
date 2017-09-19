package com.globocom.grou.entities.events.services;

import com.globocom.grou.entities.Test;
import com.globocom.grou.entities.repositories.TestRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class CallbackListenerService {

    private static final String CALLBACK_QUEUE = "grou:test_callback";

    private final Log log = LogFactory.getLog(this.getClass());

    private final TestRepository testRepository;

    @Autowired
    public CallbackListenerService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @JmsListener(destination = CALLBACK_QUEUE)
    public void callback(Test test) {
        testRepository.save(test);
        log.info("Test " + test.getName() + " status: " + test.getStatus().toString() + " (from loader " + test.getLoader() + ")");
    }
}
