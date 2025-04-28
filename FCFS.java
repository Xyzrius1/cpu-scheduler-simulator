package scheduler;

// src/FCFS.java
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FCFS extends Scheduler {

    public FCFS(List<Process> processes) {
        super(processes);
    }

    @Override
    public void run() {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.startTime = currentTime;
            currentTime += p.burstTime;
            p.completionTime = currentTime;
        }
        calculateMetrics();
    }
}

