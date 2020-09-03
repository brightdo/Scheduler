import java.util.ArrayList;
import java.util.Scanner;

public class RoundRobin {

    private Database database;

    private ArrayList allRandomNum = new ArrayList < String > ();

    public RoundRobin(ArrayList allData, ArrayList allRandomNum) {

        database = new Database(allData);


        for (int i = 0; i < allRandomNum.size(); i++) {
            this.allRandomNum.add(allRandomNum.get(i));
        }

    }


    public void output(boolean specific) {
        int runningTime = 0;
        boolean recentTerminate;
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;
        int[] blockCount = new int[database.getNumOfProcess()];
        int[] runCount = new int[database.getNumOfProcess()];
        int[] turnaroundTime = new int[database.getNumOfProcess()];
        int[] finishingTime = new int[database.getNumOfProcess()];
        int[] IOTime = new int[database.getNumOfProcess()];
        int[] waitingTime = new int[database.getNumOfProcess()];
        String[] processSituation = new String[database.getNumOfProcess()];
        int[] remainder = new int[database.getNumOfProcess()];
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


        // first few line 
        int cpuBurst;
        for (int i = 0; i < database.getNumOfProcess(); i++) {
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
                    processSituation[i] = "ready";
                    readyProcess.add(i);
                    if (Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum) > 2) {
                        processSituation[(int) readyProcess.get(0)] = "Cready";
                        remainder[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum) - 2;
                    }
                    isRunning = true;
                } else if (isRunning) {
                    processSituation[i] = "ready";
                    readyProcess.add(i);
                }
            }
        }


        if (processSituation[(int) readyProcess.get(0)].equals("ready")) {
            runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
            processSituation[(int) readyProcess.get(0)] = "running";
        }
        if (processSituation[(int) readyProcess.get(0)].contains("Cready")) {
            processSituation[(int) readyProcess.get(0)] = "Crunning";
            runCount[(int) readyProcess.get(0)] = 2;
        }
        allRandomNum.remove(0);
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            done += database.getTotalCpuTime()[i];
        }

        numOfCycle++;
        numOfCyclePrint++;



        // starting from here is the while loop
        while (done != 0) {
            done = 0;
            int readyCounter = 0;
            boolean recentReady = false;
            recentTerminate = false;
            called = false;
            boolean TotalIOTimeBool = false;
            boolean justChanged = false;
            ArrayList allReady = new ArrayList < String > ();
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
                if (processSituation[i].contains("ready")) {
                    waitingTime[i]++;
                }
                if (processSituation[i].contains("blocked")) {
                    TotalIOTimeBool = true;
                }
            }
            if (TotalIOTimeBool) {
                TotalIOTime++;
            }


            done = 0;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Round Robbin \n");
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
                if (processSituation[i].contains("Cready")) {
                    initialMessage += "ready" + " ";
                } else if (processSituation[i].contains("Crunning")) {
                    initialMessage += "running" + " ";
                } else {
                    initialMessage += processSituation[i] + " ";
                }
                if (processSituation[i].toString().contains("running")) {
                    initialMessage += runCount[i] + " ";
                } else if (processSituation[i].toString().contains("ready")) {
                    initialMessage += "0 ";
                } else if (processSituation[i].toString().contains("blocked")) {
                    initialMessage += blockCount[i] + " ";
                } else if (processSituation[i].toString().contains("terminated")) {
                    initialMessage += "0 ";
                } else if (processSituation[i].toString().contains("unstarted")) {
                    initialMessage += "0 ";
                } else if (processSituation[i].toString().contains("Crunning")) {
                    initialMessage += runCount[i] + " ";
                } else if (processSituation[i].toString().contains("Arunning")) {
                    initialMessage += runCount[i] + " ";
                } else if (processSituation[i].toString().contains("Cready")) {
                    initialMessage += "0 ";
                }
            }

            if (specific) {
                System.out.println(initialMessage);
            }
            allReady.clear();
            // runCount
            if (!readyProcess.isEmpty()) {
                if (runCount[(int) readyProcess.get(0)] > 0) {
                    if (runCount[(int) readyProcess.get(0)] - 1 == 0) {
                        boolean recentBlock = true;
                        if (processSituation[(int) readyProcess.get(0)].contains("Crunning") && remainder[(int) readyProcess.get(0)] > 0) {
                            allReady.add(readyProcess.get(0));
                        }
                    }
                    runCount[(int) readyProcess.get(0)]--;
                }

                for (int i = 0; i < readyProcess.size(); i++) {
                    if (processSituation[(int) readyProcess.get(i)].contains("terminated")) {
                        readyProcess.remove(i);
                    }
                }
                // totalCpuTime Count
                if (database.getTotalCpuTime()[(int) readyProcess.get(0)] > 0) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(0)] - 1 == 0) {
                        recentTerminate = true;
                    }
                    database.getTotalCpuTime()[(int) readyProcess.get(0)]--;
                    if (recentTerminate) {

                        processSituation[(int) readyProcess.get(0)] = "terminated";
                        finishingTime[(int) readyProcess.get(0)] = numOfCycle;
                        readyProcess.remove(0);
                    }
                }
            }

            //  unstarted
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (processSituation[i].contains("unstarted") && database.getArrivalTime()[i] == numOfCycle) {
                    allReady.add(i);
                    processSituation[i] = "ready";
                    readyProcess.add(i);
                }
            }

            //block count
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (blockCount[i] > 0) {
                    blockCount[i]--;
                    if (blockCount[i] == 0) {
                        allReady.add(i);
                    }
                }
            }

            done = 0;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Round Robbin \n");
                break;
            }


            //from run to block
            if (!readyProcess.isEmpty()) {
                if (runCount[(int) readyProcess.get(0)] == 0) {
                    if (!processSituation[(int) readyProcess.get(0)].contains("terminated") || !processSituation[(int) readyProcess.get(0)].contains("unstarted") || !processSituation[(int) readyProcess.get(0)].contains("blocked")) {
                        if (processSituation[(int) readyProcess.get(0)].contains("Crunning")) {
                            if (remainder[(int) readyProcess.get(0)] > 0) {
                                processSituation[(int) readyProcess.get(0)] = "Cready";
                                readyProcess.add(readyProcess.get(0));
                                readyProcess.remove(0);
                            } else if (remainder[(int) readyProcess.get(0)] == 0) {
                                processSituation[(int) readyProcess.get(0)] = "blocked";
                                blockCount[(int) readyProcess.get(0)] = Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                                IOTime[(int) readyProcess.get(0)] += Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                                if (specific) {
                                    System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
                                }
                                readyProcess.remove(0);
                                allRandomNum.remove(0);
                            }
                        } else if (processSituation[(int) readyProcess.get(0)].contains("running")) {
                            processSituation[(int) readyProcess.get(0)] = "blocked";
                            blockCount[(int) readyProcess.get(0)] = Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                            IOTime[(int) readyProcess.get(0)] += Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                            if (specific) {
                                System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
                            }
                            readyProcess.remove(0);
                            allRandomNum.remove(0);
                        }
                    }
                }
            }


            //block to ready
            if (!readyProcess.isEmpty()) {
                for (int i = 0; i < database.getNumOfProcess(); i++) {
                    if (blockCount[i] == 0) {
                        if (processSituation[i].contains("blocked")) {
                            processSituation[i] = "ready";
                            readyProcess.add(i);
                        }
                    }
                }
            } else if (readyProcess.isEmpty()) {
                for (int i = 0; i < database.getNumOfProcess(); i++) {
                    if (blockCount[i] == 0) {
                        if (processSituation[i].contains("blocked")) {
                            processSituation[i] = "ready";
                            readyProcess.add(i);
                        }
                    }
                }
            }

            //tie breaker

            int index = 0;
            if (allReady.size() > 1) {
                for (int i = 0; i < allReady.size(); i++) {
                    for (int j = i + 1; j < allReady.size(); j++) {
                        if (database.getArrivalTime()[(int) allReady.get(i)] > database.getArrivalTime()[(int) allReady.get(j)]) {
                            index = (int) allReady.get(i);
                            allReady.set(i, allReady.get(j));
                            allReady.set(j, index);
                        }
                        if (database.getArrivalTime()[(int) allReady.get(i)] == database.getArrivalTime()[(int) allReady.get(j)]) {
                            if ((int) allReady.get(i) > (int) allReady.get(j)) {
                                index = (int) allReady.get(i);
                                allReady.set(i, allReady.get(j));
                                allReady.set(j, index);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < allReady.size(); i++) {
                readyProcess.remove(allReady.get(i));
                readyProcess.add(allReady.get(i));
            }



            //ready to run


            if (!readyProcess.isEmpty()) {
                if (processSituation[(int) readyProcess.get(0)].contains("Cready")) {
                    if (remainder[(int) readyProcess.get(0)] > 0) {
                        processSituation[(int) readyProcess.get(0)] = "Crunning";
                        if (remainder[(int) readyProcess.get(0)] > 2) {
                            runCount[(int) readyProcess.get(0)] = 2;
                            remainder[(int) readyProcess.get(0)] -= 2;
                        } else {
                            runCount[(int) readyProcess.get(0)] = remainder[(int) readyProcess.get(0)];
                            remainder[(int) readyProcess.get(0)] = 0;
                        }
                    } else {
                        processSituation[(int) readyProcess.get(0)] = "running";

                    }
                } else if (processSituation[(int) readyProcess.get(0)].contains("ready")) {
                    if (remainder[(int) readyProcess.get(0)] == 0) {
                        if (Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum) > 2) {
                            if (specific) {
                                System.out.println("Find burst when choosing ready Process to run " + allRandomNum.get(0));
                            }
                            processSituation[(int) readyProcess.get(0)] = "Crunning";
                            runCount[(int) readyProcess.get(0)] = 2;
                            remainder[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum) - 2;
                            allRandomNum.remove(0);
                        } else {
                            processSituation[(int) readyProcess.get(0)] = "running";
                            if (specific) {
                                System.out.println("Find burst when choosing ready Process to run " + allRandomNum.get(0));
                            }
                            runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
                            allRandomNum.remove(0);
                        }
                    }
                }
            }
            done = 0;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Round Robbin \n");
                break;
            }
            numOfCycle++;
            numOfCyclePrint++;
        }


        for (int i = 0; i < database.getNumOfProcess(); i++) {
            runningTime += database.getForTotalCpuTime()[i];
            totalWaitingTime += waitingTime[i];
        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            turnaroundTime[i] = finishingTime[i] - database.getArrivalTime()[i];
        }
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            System.out.println("Process " + i + ":");
            System.out.println("     (A,B,C,IO) = " + "(" + database.getForArrivalTime()[i] + ", " + database.getForB()[i] + ", " + database.getForTotalCpuTime()[i] + ", " + database.getForIO()[i] + ")");
            System.out.println("     Finishing time: " + finishingTime[i]);
            System.out.println("     turnaround time: " + turnaroundTime[i]);
            totalTurnAroundTime += turnaroundTime[i];
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
        System.out.format("     Average waiting time: %.6f%n", ((double)(totalWaitingTime) / (double)(database.getNumOfProcess())));

    }
}