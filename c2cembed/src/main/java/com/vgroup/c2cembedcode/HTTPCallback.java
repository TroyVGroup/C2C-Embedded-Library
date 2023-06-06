package com.vgroup.c2cembedcode;

public interface HTTPCallback {
    void processFinish(Object clazz);
    void processFailed(int responseCode, String output);
}
