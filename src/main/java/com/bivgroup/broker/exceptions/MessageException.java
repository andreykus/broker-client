package com.bivgroup.broker.exceptions;

/**
 * Created by bush on 12.05.2016.
 * Исключение менеджера работы с сообщениями
 */

public class MessageException extends Exception {

    private String mewebMessage = "";

    public MessageException() {
    }

    public MessageException(String msg) {
        super(msg);
    }

    public MessageException(String msg, String mewebMessage) {
        super(msg);
        this.mewebMessage = mewebMessage;
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public String getmeMessage() {
        return mewebMessage;
    }
}
