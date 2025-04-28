package scheduler;

// src/SRTF.java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRTF extends Scheduler {

    public SRTF(List<Process> processes) {
        super(processes);
    }

    @Override
    public void run() {
        List<Process> arrived = new ArrayList<>();
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {
            for (Process p : processes) {
                if (p.arrivalTime == currentTime) {
                    arrived.add(p);
                }
            }

            Process shortest = arrived.stream()
                    .filter(p -> p.remainingTime > 0)
                    .min(Comparator.comparingInt(p -> p.remainingTime))
                    .orElse(null);

            if (shortest == null) {
                currentTime++;
                continue;
            }

            if (shortest.startTime == -1) {
                shortest.startTime = currentTime;
            }

            shortest.remainingTime--;
            currentTime++;

            if (shortest.remainingTime == 0) {
                shortest.completionTime = currentTime;
                completed++;
            }
        }
        calculateMetrics();
    }
}

