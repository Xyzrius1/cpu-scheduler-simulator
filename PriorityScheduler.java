package scheduler;

// src/PriorityScheduler.java
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduler extends Scheduler {

    public PriorityScheduler(List<Process> processes) {
        super(processes);
    }

    @Override
    public void run() {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {
            Process highestPriority = null;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (highestPriority == null || p.priority < highestPriority.priority) {
                        highestPriority = p;
                    }
                }
            }

            if (highestPriority == null) {
                currentTime++;
                continue;
            }

            highestPriority.startTime = currentTime;
            currentTime += highestPriority.burstTime;
            highestPriority.completionTime = currentTime;
            highestPriority.remainingTime = 0;
            completed++;
        }
        calculateMetrics();
    }
}
