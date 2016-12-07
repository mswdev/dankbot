package client.api.util;

/**
 * Created by Spencer on 11/12/2016.
 */
public class Timer {

    private long startTime;
    private long actualStartTime;

    public Timer() {
        actualStartTime = System.currentTimeMillis();
        set();
    }

    public void set() {
        this.startTime = System.currentTimeMillis();
    }

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public long getElapsedAndSet() {
        long elapsed = getElapsed();
        set();
        return elapsed;
    }

    public long getTotalTime() {
        return System.currentTimeMillis() - actualStartTime;
    }
}
