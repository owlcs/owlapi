package org.semanticweb.owlapi.reasoner;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Jan-2010
 */
public class TimedConsoleProgressMonitor implements ReasonerProgressMonitor {

    private int lastPercentage = 0;
    private long lastTime;
    private ThreadMXBean bean=ManagementFactory.getThreadMXBean();
    private long beginTime;

    public void reasonerTaskStarted(String taskName) {
        System.out.print(taskName);
        System.out.println(" ...");
        lastTime=bean.getCurrentThreadCpuTime();
        beginTime=lastTime;
    }

    public void reasonerTaskStopped() {
        System.out.println("    ... finished in "+((bean.getCurrentThreadCpuTime()-beginTime))/1000000D);
        lastPercentage = 0;
        
    }

    public void reasonerTaskProgressChanged(int value, int max) {
    	long time=bean.getCurrentThreadCpuTime();
        if (max > 0) {
            int percent = (value * 100) / max;
            if (lastPercentage != percent) {
                System.out.println("    "+percent+"%\t"+((time-lastTime)/1000000));
                lastTime=time;
                lastPercentage = percent;
            }
        }
    }

    public void reasonerTaskBusy() {
        System.out.println("    busy ...");
    }
}
