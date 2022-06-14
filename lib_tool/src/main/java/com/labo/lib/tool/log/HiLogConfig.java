package com.labo.lib.tool.log;

public abstract class HiLogConfig {

    static int MAX_LEN = 512;
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    public boolean enable() {
        return true;
    }

    public boolean includeThread() {
        return false;
    }

    public int stackTraceDepth() {
        return 5;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

}
