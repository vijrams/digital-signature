package com.wk.elms.ebilling.digsig.api

import com.wk.elms.ebilling.digsig.api.service.DigSigService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.SmartLifecycle
import org.springframework.stereotype.Component

/**
 * Created by ranadeep.palle on 4/17/2017.
 */
@Component
class ConfigurationValidator implements SmartLifecycle {
    private final static Logger LOGGER = LoggerFactory.getLogger(this.class)

    private volatile boolean isRunning = false

    @Autowired
    private ApplicationContext applicationContext

    @Autowired
    private DigSigService digSigService

    @Override
    boolean isAutoStartup() {
        return true
    }

    @Override
    void stop(Runnable callback) {
        isRunning = false
        new Thread(callback).start()
    }

    @Override
    void start() {
        isRunning = true
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Starting DigSig Service...")
        }
    }

    @Override
    void stop() {
        isRunning = false
    }

    @Override
    boolean isRunning() {
        return isRunning
    }

    @Override
    int getPhase() {
        return 1
    }
}

