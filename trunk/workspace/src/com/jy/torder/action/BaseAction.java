package com.jy.torder.action;

public interface BaseAction {
    //    messageType:
    //    ERROR_MESSAGE 
    //    INFORMATION_MESSAGE 
    //    WARNING_MESSAGE 
    //    QUESTION_MESSAGE 
    //    PLAIN_MESSAGE
    void processMessage(int messageType, String message, Throwable tr);
}
