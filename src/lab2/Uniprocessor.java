import java.util.ArrayList;
import java.util.Scanner;

public class Uniprocessor {

    private Database database;

    private ArrayList allRandomNum = new ArrayList < String > ();

    public Uniprocessor(ArrayList allData, ArrayList allRandomNum) {

        database = new Database(allData);


        for (int i = 0; i < allRandomNum.size(); i++) {
            this.allRandomNum.add(allRandomNum.get(i));
        }

    }


    public void output(boolean specific) {
        int[] blockCount = new int[database.getNumOfProcess()];
        int[] runCount = new int[database.getNumOfProcess()];
        int[] turnaroundTime = new int[database.getNumOfProcess()];
        int[] finishingTime = new int[database.getNumOfProcess()];
        int[] IOTime = new int[database.getNumOfProcess()];
        int[] waitingTime = new int[database.getNumOfProcess()];
        String[] processSituation = new String[database.getNumOfProcess()];
        int runningTime = 0;
        boolean recentTerminate;
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;
        int TotalIOTime = 0;
        String anyThingRunning = "";
        boolean called = false;
        boolean forReadyProcess = false;
        int counter = 0; // counter for putting data from input into array
        int numOfCycle = 0;
        int numOfCyclePrint = 0;
        int done = 0; // to stop program when all processes are done
        boolean isRunning = false;
        ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
        String initialMessage = "Before Cycle  " + numOfCycle + ":  ";

        String[] indviData = null;
        String[] splitFile = null;
        //for randomNumber
        Scanner input = null;

        // first few line 
        int cpuBurst;
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            finishingTime[i] = -100;
            processSituation[i] = "unstarted";
            initialMessage += processSituation[i] + " " + runCount[i] + " ";
        }

        if (specific) {
            System.out.println(initialMessage);
            System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            if (database.getArrivalTime()[i] == numOfCycle) {
                if (!isRunning) {
                    processSituation[i] = "running";
                    isRunning = true;
                } else if (isRunning) {
                    processSituation[i] = "ready";
                }
            }
        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            readyProcess.add(i);
        }
        numOfCycle++;
        numOfCyclePrint++;

        runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
        allRandomNum.remove(0);
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            done += database.getTotalCpuTime()[i];
        }


        // starting from here is the while loop
        while (done != 0) {
            done = 0;
            recentTerminate = false;
            called = false;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
                if (processSituation[i].contains("ready")) {
                    waitingTime[i]++;
                }
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Uniprocessor\n");
                break;
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (database.getTotalCpuTime()[i] == 0) {
                    if (finishingTime[i] == -100) {
                        finishingTime[i] = numOfCycle;
                        turnaroundTime[i] = finishingTime[i] - database.getArrivalTime()[i];
                    }
                }
            }
            initialMessage = "Before Cycle  " + numOfCyclePrint + ":  ";
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                initialMessage += processSituation[i] + " ";
                if (processSituation[i].toString().contains("running")) {
                    initialMessage += runCount[i];
                } else if (processSituation[i].toString().contains("ready")) {
                    initialMessage += "0 ";
                } else if (processSituation[i].toString().contains("blocked")) {
                    initialMessage += blockCount[i];
                } else if (processSituation[i].toString().contains("terminated")) {
                    initialMessage += "0 ";
                } else if (processSituation[i].toString().contains("unstarted")) {
                    initialMessage += "0 ";
                }
            }
            if (specific) {
                System.out.println(initialMessage);
            }
            if (!readyProcess.isEmpty() && processSituation[(int) readyProcess.get(0)].contains("running")) {
                if (runCount[(int) readyProcess.get(0)] > 0) {
                    runCount[(int) readyProcess.get(0)]--;
                }
                if (database.getTotalCpuTime()[(int) readyProcess.get(0)] > 0) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(0)] - 1 == 0) {
                        recentTerminate = true;
                    }
                    database.getTotalCpuTime()[(int) readyProcess.get(0)]--;
                }
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (processSituation[i].contains("unstarted") && database.getArrivalTime()[i] == numOfCycle) {
                    processSituation[i] = "ready";
                }
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (blockCount[i] > 0) {
                    blockCount[i]--;
                }
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (database.getTotalCpuTime()[i] == 0) {
                    processSituation[i] = "terminated";
                    if (finishingTime[i] == -100) {
                        finishingTime[i] = numOfCycle;
                        turnaroundTime[i] = finishingTime[i] - database.getArrivalTime()[i];
                    }
                }
            }
            if (!readyProcess.isEmpty() && recentTerminate) {
                readyProcess.remove(0);
                if (!readyProcess.isEmpty()) {
                    processSituation[(int) readyProcess.get(0)] = "running";
                    runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
                    allRandomNum.remove(0);
                }
            }

            done = 0;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Uniprocessor\n");
                break;
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (blockCount[i] == 0 && !readyProcess.contains(i)) {
                    if (!processSituation[i].contains("terminated") && !processSituation[i].contains("unstarted")) {
                        processSituation[i] = "ready";
                    }
                }
            }
            anyThingRunning = "";
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                anyThingRunning += processSituation[i];
            }
            if (!readyProcess.isEmpty() && blockCount[(int) readyProcess.get(0)] == 0 && processSituation[(int) readyProcess.get(0)].contains("blocked")) {
                processSituation[(int) readyProcess.get(0)] = "running";
                runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
                if (specific) {
                    System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
                }
                allRandomNum.remove(0);
            }


            if (!readyProcess.isEmpty() && !recentTerminate) {
                if (runCount[(int) readyProcess.get(0)] == 0 && blockCount[(int) readyProcess.get(0)] == 0) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(0)] != 0) {
                        processSituation[(int) readyProcess.get(0)] = "blocked";
                        if (specific) {
                            System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
                        }
                        blockCount[(int) readyProcess.get(0)] = Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                        IOTime[(int) readyProcess.get(0)] += Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                        allRandomNum.remove(0);
                    }
                }
            }


            numOfCycle++;
            numOfCyclePrint++;
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Uniprocessor\n");
                break;
            }

        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            runningTime += database.getForTotalCpuTime()[i];
            TotalIOTime += IOTime[i];
            totalTurnAroundTime += turnaroundTime[i];
            totalWaitingTime += waitingTime[i];
        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            System.out.println("Process " + i + ":");
            System.out.println("     (A,B,C,IO) = " + "(" + database.getForArrivalTime()[i] + ", " + database.getForB()[i] + ", " + database.getForTotalCpuTime()[i] + ", " + database.getForIO()[i] + ")");
            System.out.println("     Finishing time: " + finishingTime[i]);
            System.out.println("     turnaround time: " + turnaroundTime[i]);
            System.out.println("     I/O time: " + IOTime[i]);
            System.out.println("     waiting time " + waitingTime[i]);
            System.out.println("\n");
        }

        System.out.println("Summary Data:");
        System.out.println("     Finishing time: " + numOfCycle);
        System.out.format("     CPU Utillization: %.6f%n", ((double) runningTime) / (double)(numOfCycle));
        System.out.format("     I/O Utilizaation: %.6f%n", ((double)(TotalIOTime) / (double)(numOfCycle)));
        System.out.format("     ThroughPut: %.6f processes per hundred cycles%n", (100.0 / (double)(numOfCycle) * database.getNumOfProcess()));
        System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime) / (double)(database.getNumOfProcess())));
        System.out.format("     Average waiting time: %.6f%n \n", ((double)(totalWaitingTime) / (double)(database.getNumOfProcess())));

    }
}