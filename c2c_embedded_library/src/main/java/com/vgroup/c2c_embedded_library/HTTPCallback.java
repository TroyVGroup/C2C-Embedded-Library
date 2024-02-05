package com.vgroup.c2c_embedded_library;

public interface HTTPCallback {
    void processFinish(Object clazz);
    void processFailed(int responseCode, String output);
}
