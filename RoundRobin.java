package scheduler;

// src/RoundRobin.java
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class RoundRobin extends Scheduler {
    private int timeQuantum;

    public RoundRobin(List<Process> processes, int timeQuantum) {
        super(processes);
        this.timeQuantum = timeQuantum;
    }

    @Override
    public void run() {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {
            for (Process p : processes) {
                if (p.arrivalTime == currentTime) {
                    queue.add(p);
                }
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process current = queue.poll();
            if (current.startTime == -1) {
                current.startTime = currentTime;
            }

            int execTime = Math.min(current.remainingTime, timeQuantum);
            currentTime += execTime;
            current.remainingTime -= execTime;

            for (Process p : processes) {
                if (p.arrivalTime > current.startTime && p.arrivalTime <= currentTime && !queue.contains(p)) {
                    queue.add(p);
                }
            }

            if (current.remainingTime > 0) {
                queue.add(current);
            } else {
                current.completionTime = currentTime;
                completed++;
            }
        }
        calculateMetrics();
    }
}

