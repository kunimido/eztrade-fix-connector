package com.github.kunimido.eztrade.fix.connector;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import quickfix.DefaultMessageFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MemoryStoreFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SLF4JLogFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

public class FixConnectionIT {
    private static Initiator initiator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        final MessageStoreFactory messageStoreFactory = new MemoryStoreFactory();
        final SessionSettings settings = new SessionSettings("quickfix-test.ini");
        final LogFactory logFactory = new SLF4JLogFactory(settings);
        final MessageFactory messageFactory = new DefaultMessageFactory();
        initiator = new SocketInitiator(new TestFixSessionListener(), messageStoreFactory, settings, logFactory, messageFactory);
        initiator.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        initiator.stop(true);
    }

    @Test
    public void shouldBeLoggedOnAfterStart() throws Exception {
        int retries = 0;
        do {
            Thread.sleep(100);
        } while (retries++ < 99 || !initiator.isLoggedOn());
        Assert.assertTrue(initiator.isLoggedOn());
    }
}