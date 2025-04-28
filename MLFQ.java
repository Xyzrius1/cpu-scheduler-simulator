package scheduler;

// src/MLFQ.java
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class MLFQ extends Scheduler {
    private int timeQuantum1 = 4;
    private int timeQuantum2 = 8;

    public MLFQ(List<Process> processes) {
        super(processes);
    }

    @Override
    public void run() {
        Queue<Process> q1 = new LinkedList<>();
        Queue<Process> q2 = new LinkedList<>();
        Queue<Process> q3 = new LinkedList<>();

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        while (completed < n) {
            for (Process p : processes) {
                if (p.arrivalTime == currentTime) {
                    q1.add(p);
                }
            }

            Process current = null;
            if (!q1.isEmpty()) current = q1.poll();
            else if (!q2.isEmpty()) current = q2.poll();
            else if (!q3.isEmpty()) current = q3.poll();

            if (current == null) {
                currentTime++;
                continue;
            }

            if (current.startTime == -1) {
                current.startTime = currentTime;
            }

            int quantum = q1.contains(current) ? timeQuantum1 : (q2.contains(current) ? timeQuantum2 : current.remainingTime);
            int execTime = Math.min(current.remainingTime, quantum);
            currentTime += execTime;
            current.remainingTime -= execTime;

            for (Process p : processes) {
                if (p.arrivalTime > current.startTime && p.arrivalTime <= currentTime) {
                    q1.add(p);
                }
            }

            if (current.remainingTime > 0) {
                if (quantum == timeQuantum1) q2.add(current);
                else q3.add(current);
            } else {
                current.completionTime = currentTime;
                completed++;
            }
        }
        calculateMetrics();
    }
}

