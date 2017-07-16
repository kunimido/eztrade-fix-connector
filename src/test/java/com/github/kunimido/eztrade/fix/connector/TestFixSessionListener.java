package com.github.kunimido.eztrade.fix.connector;

import lombok.extern.slf4j.Slf4j;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

@Slf4j
public class TestFixSessionListener implements quickfix.Application {
    @Override
    public void onCreate(final SessionID sessionId) {
        log.info("onCreate: " + sessionId.toString());
    }

    @Override
    public void onLogon(final SessionID sessionId) {
        log.info("onLogon: " + sessionId.toString());
    }

    @Override
    public void onLogout(final SessionID sessionId) {
        log.info("onLogout: " + sessionId.toString());
    }

    @Override
    public void toAdmin(final Message message, final SessionID sessionId) {
        log.info("toAdmin: " + message.toString().replace('\u0001', '|'));
    }

    @Override
    public void fromAdmin(final Message message, final SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("fromAdmin: " + message.toString().replace('\u0001', '|'));
    }

    @Override
    public void toApp(final Message message, final SessionID sessionId) throws DoNotSend {
        log.info("toApp: " + message.toString().replace('\u0001', '|'));
    }

    @Override
    public void fromApp(final Message message, final SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("fromApp: " + message.toString().replace('\u0001', '|'));
    }
}