package scheduler;

// src/Scheduler.java
import java.util.List;

public abstract class Scheduler {
    protected List<Process> processes;

    public Scheduler(List<Process> processes) {
        this.processes = processes;
    }

    public abstract void run();

    protected void calculateMetrics() {
        int totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime);
        }
        System.out.println("\nAverage Waiting Time: " + (float)totalWT / processes.size());
        System.out.println("Average Turnaround Time: " + (float)totalTAT / processes.size());
    }
}

