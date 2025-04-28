package scheduler;

// src/Main.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process " + (i + 1));
            System.out.print("Arrival Time: ");
            int at = sc.nextInt();
            System.out.print("Burst Time: ");
            int bt = sc.nextInt();
            System.out.print("Priority: ");
            int pr = sc.nextInt();
            processes.add(new Process(i + 1, at, bt, pr));
        }

        System.out.println("\nSelect Scheduling Algorithm:");
        System.out.println("1. FCFS");
        System.out.println("2. SRTF");
        System.out.println("3. Priority Scheduling");
        System.out.println("4. Round Robin");
        System.out.println("5. MLFQ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                new FCFS(processes).run();
                break;
            case 2:
                new SRTF(processes).run();
                break;
            case 3:
                new PriorityScheduler(processes).run();
                break;
            case 4:
                System.out.print("Enter Time Quantum: ");
                int tq = sc.nextInt();
                new RoundRobin(processes, tq).run();
                break;
            case 5:
                new MLFQ(processes).run();
                break;
            default:
                System.out.println("Invalid Choice!");
        }

        sc.close();
    }
}

