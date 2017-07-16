package com.github.kunimido.eztrade.fix.connector;

import javax.annotation.PreDestroy;
import javax.management.ObjectName;
import org.quickfixj.jmx.JmxExporter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import quickfix.Acceptor;
import quickfix.DefaultMessageFactory;
import quickfix.LogFactory;
import quickfix.MemoryStoreFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SLF4JLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

@Component
public class FixSessionRunner implements ApplicationRunner {
    private final FixSessionListener fixSessionListener;

    private Acceptor acceptor;

    private ObjectName acceptorJmxObjectName;

    public FixSessionRunner() {
        fixSessionListener = new FixSessionListener();
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        final MessageStoreFactory messageStoreFactory = new MemoryStoreFactory();
        final SessionSettings settings = new SessionSettings("quickfix.ini");
        final LogFactory logFactory = new SLF4JLogFactory(settings);
        final MessageFactory messageFactory = new DefaultMessageFactory();
        acceptor = new SocketAcceptor(fixSessionListener, messageStoreFactory, settings, logFactory, messageFactory);
        acceptorJmxObjectName = new JmxExporter().register(acceptor);
        acceptor.start();
    }

    @PreDestroy
    public void stop() throws Exception {
        new JmxExporter().getMBeanServer().unregisterMBean(acceptorJmxObjectName);
        acceptor.stop();
    }
}