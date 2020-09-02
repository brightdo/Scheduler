import java.util.ArrayList;
import java.util.Scanner;

public class PSJF {
	

	private Database database;

    private ArrayList allRandomNum = new ArrayList <String> ();
	
    public PSJF(ArrayList allData, ArrayList allRandomNum) {
    	
		database =  new Database(allData);


		for(int i=0; i<allRandomNum.size();i++) {
			this.allRandomNum.add(allRandomNum.get(i));
		}
		
	}
	
	
	public void output(boolean specific){
        int[] blockCount = new int[database.getNumOfProcess()];
        int[] runCount = new int[database.getNumOfProcess()];
        int[] turnaroundTime = new int[database.getNumOfProcess()];
        int[] finishingTime = new int[database.getNumOfProcess()];
        int[] IOTime = new int[database.getNumOfProcess()];
        int[] waitingTime = new int[database.getNumOfProcess()];
        String[]  processSituation = new String[database.getNumOfProcess()];
        int runningTime = 0;
        boolean recentTerminate;
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;
        int index = 0;
        boolean remaining = false;
        boolean tieBreaker = false;
        int TotalIOTime = 0;
        int readyCounter = 0;
        String anyThingRunning = "";
        boolean called = false;
        boolean recentReady = false;
        int counter = 0; // counter for putting data from input into array
        int numOfCycle = 0;
        int numOfCyclePrint = 0;
        boolean justChanged;
        int done = 0; // to stop program when all processes are done
        boolean isRunning = false;
        ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
        ArrayList compareList = new ArrayList < String > ();
        ArrayList copyList = new ArrayList < String > ();
        String initialMessage = "Before Cycle  " + numOfCycle + ":  ";



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
                    processSituation[i] = "ready";
                    readyProcess.add(i);
                    isRunning = true;
                } else if (isRunning) {
                    processSituation[i] = "ready";
                    for (int j = 0; j < readyProcess.size(); j++) {
                        if (database.getTotalCpuTime()[i] < database.getTotalCpuTime()[(int) readyProcess.get(j)]) {
                            if (!readyProcess.contains(i)) {
                                readyProcess.add(j, i);
                            }
                        } else {
                            if (!readyProcess.contains(i)) {
                                readyProcess.add(i);
                            }
                        }
                    }
                }
            }
        }
        processSituation[(int) readyProcess.get(0)] = "running";
        numOfCycle++;
        numOfCyclePrint++;
        runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)],allRandomNum);
        allRandomNum.remove(0);
        for (int i = 0; i < database.getNumOfProcess(); i++) {
            done += database.getTotalCpuTime()[i];
        }




        // starting from here is the while loop
        boolean TotalIOTimeBool;
        while (done != 0) {
            done = 0;
            readyCounter = 0;
            recentReady = false;
            recentTerminate = false;
            called = false;
            TotalIOTimeBool = false;
            justChanged = false;
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


            if (done == 0) {
                System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
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
                    initialMessage += runCount[i];
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

            if (!readyProcess.isEmpty()) {
                if (runCount[(int) readyProcess.get(0)] > 0) {
                    runCount[(int) readyProcess.get(0)]--;
                }
                if (database.getTotalCpuTime()[(int) readyProcess.get(0)] > 0) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(0)] - 1 == 0) {
                        recentTerminate = true;
                    }
                    database.getTotalCpuTime()[(int) readyProcess.get(0)]--;
                    if (recentTerminate) {
                        processSituation[(int) readyProcess.get(0)] = "terminated";
                        readyProcess.remove(0);
                    }
                }
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (processSituation[i].contains("unstarted") && database.getArrivalTime()[i] == numOfCycle) {
                    processSituation[i] = "ready";
                }
            }
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (blockCount[i] > 0) {
                    if (blockCount[i] - 1 == 0) {
                        justChanged = true;
                        index = i;
                    }
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
            }
            done = 0;
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                done += database.getTotalCpuTime()[i];
            }
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
                break;
            }


            // block
            int initalIndex = -1;
            int smallestOne = 0;
            if (!readyProcess.isEmpty() && !recentTerminate) {
                if (runCount[(int) readyProcess.get(0)] == 0) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(0)] != 0) {
                        processSituation[(int) readyProcess.get(0)] = "blocked";
                        if (specific) {
                            System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
                        }
                        blockCount[(int) readyProcess.get(0)] = Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                        IOTime[(int) readyProcess.get(0)] += Main.randomOS(database.getIO()[(int) readyProcess.get(0)], allRandomNum);
                        allRandomNum.remove(0);
                        readyProcess.remove(0);
                    }
                }
            }
            ///////////////////////////end of blocking




            //ready
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (blockCount[i] == 0) {
                    if (!processSituation[i].contains("terminated") && !processSituation[i].contains("unstarted")) {
                        processSituation[i] = "ready";
                    }
                }
            }


            for (int i = 0; i < database.getNumOfProcess(); i++) {
                if (!processSituation[i].contains("terminated") && !processSituation[i].contains("unstarted")) {
                    if (blockCount[i] == 0) {
                        if (!readyProcess.contains(i))
                            readyProcess.add(i);
                    }
                } //////////////////////////////////////////////end of ready
            }

            // sort readyList before choosing to run;
            if (!readyProcess.isEmpty()) {
                compareList.add(readyProcess.get(0));
            }
            for (int i = 0; i < readyProcess.size(); i++) {
                for (int j = 0; j < compareList.size(); j++) {
                    if (database.getTotalCpuTime()[(int) readyProcess.get(i)] < database.getTotalCpuTime()[(int) compareList.get(j)]) {
                        if (!compareList.contains(readyProcess.get(i))) {
                            compareList.add(j, readyProcess.get(i));
                        }
                    } else if (database.getTotalCpuTime()[(int) readyProcess.get(i)] > database.getTotalCpuTime()[(int) compareList.get(j)]) {
                        if (!compareList.contains(readyProcess.get(i))) {
                            compareList.add(j + 1, readyProcess.get(i));
                        }
                    } else {
                        if (database.getArrivalTime()[(int) readyProcess.get(i)] < database.getArrivalTime()[(int) readyProcess.get(j)]) {
                            if (!compareList.contains(readyProcess.get(i))) {
                                compareList.add(j, readyProcess.get(i));
                            }
                        } else if (database.getArrivalTime()[(int) readyProcess.get(i)] > database.getArrivalTime()[(int) readyProcess.get(j)]) {
                            if (!compareList.contains(readyProcess.get(i))) {
                                compareList.add(j + 1, readyProcess.get(i));
                            }
                        } else {
                            if ((int) readyProcess.get(i) < (int) readyProcess.get(j)) {
                                if (!compareList.contains(readyProcess.get(i))) {
                                    compareList.add(j, readyProcess.get(i));
                                }
                            } else {
                                if (!compareList.contains(readyProcess.get(i))) {
                                    compareList.add(j + 1, readyProcess.get(i));
                                }
                            }
                        }
                    }
                }
            }
            readyProcess.clear();
            for (int i = 0; i < compareList.size(); i++) {
                readyProcess.add(i, compareList.get(i));
            }
            compareList.clear();

            anyThingRunning = "";
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                anyThingRunning += processSituation[i];
            }





            copyList.clear();
            for (int i = 0; i < readyProcess.size(); i++) {
                copyList.add(i, readyProcess.get(i));
            }


            for (int i = 0; i < readyProcess.size(); i++) {
                if (processSituation[(int) readyProcess.get(i)].contains("terminated")) {
                    copyList.remove(readyProcess.get(i));
                }
            }



            readyProcess.clear();
            for (int i = 0; i < copyList.size(); i++) {
                readyProcess.add(i, copyList.get(i));
            }



            anyThingRunning = "";
            for (int i = 0; i < database.getNumOfProcess(); i++) {
                anyThingRunning += processSituation[i];
            }





            if (!readyProcess.isEmpty()) {
                for (int i = 0; i < database.getNumOfProcess(); i++) {
                    if (processSituation[(int) readyProcess.get(0)].contains("ready") && runCount[(int) readyProcess.get(0)] != 0) {
                        processSituation[(int) readyProcess.get(0)] = "running";
                    }
                }
                if (processSituation[(int) readyProcess.get(0)].contains("ready") && runCount[(int) readyProcess.get(0)] == 0) {
                    if (!anyThingRunning.contains("running") && !readyProcess.isEmpty()) {
                        processSituation[(int) readyProcess.get(0)] = "running";
                        runCount[(int) readyProcess.get(0)] = Main.randomOS(database.getB()[(int) readyProcess.get(0)], allRandomNum);
                        if (specific) {
                            System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
                        }
                        allRandomNum.remove(0);
                    }
                }
            }


            numOfCycle++;
            numOfCyclePrint++;
            if (done == 0) {
                System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
                break;
            }

        }


        for (int i = 0; i < database.getNumOfProcess(); i++) {
            runningTime += database.getForTotalCpuTime()[i];
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
