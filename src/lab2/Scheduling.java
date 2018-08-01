package lab2;
import java.io.*;
import java.util.*;

public class Scheduling {

	static ArrayList allRandomNum = new ArrayList<String>();
	static ArrayList allRandomNumUni = new ArrayList<String>();
	static ArrayList allRandomNumSRTN = new ArrayList<String>();
	static ArrayList allRandomNumRobin = new ArrayList<String>();
	static ArrayList allData = new ArrayList<String>();
	static ArrayList allDataRobin = new ArrayList<String>();
	static ArrayList allDataUni = new ArrayList<String>();
	static ArrayList allDataSRTN = new ArrayList<String>();

	static int randomNumCounter=0;
	static int[] finishingTime;
	static int[] turnaroundTime;
	static int[] IOTime;
	static int numOfProcess; 
	static int[] ForArrivalTime;
	static int[] ForB;
	static int[] ForIO;
	static int[] ForTotalCpuTime;
	static int[] waitingTime;
	static boolean specific;
	public static Scanner newScanner(String fileName) {
		 try{
			 Scanner input = new Scanner(new BufferedReader(new FileReader(fileName)));
			 return input;
		 }
		 catch(Exception ex) {
			 System.out.printf("Error reading %s\n", fileName);
			 System.exit(0);
		 }
		 return null;
	}
	
	
	public static void DataInitiate(Scanner filename, Scanner otherFile) {
		String[] indviData = null;
		String[] splitFile = null;
		while(otherFile.hasNext()) {
			 String line = otherFile.next();
			 splitFile = line.split("\\s+");			
			 for(int i = 0; i < splitFile.length; i++){
				 if(!"".equals(splitFile[i])) {					
					 allRandomNum.add(splitFile[i]);
					 allRandomNumUni.add(splitFile[i]);
					 allRandomNumSRTN.add(splitFile[i]);
					 allRandomNumRobin.add(splitFile[i]);
				 }
			 }
		}

		 while (filename.hasNext()) 
		 {
			 String currentLine = filename.next();
			 indviData = currentLine.split("\\s+");	
			 for(int i = 0; i < indviData.length; i++){
				 if(!"".equals(indviData[i])) {	
					 allData.add(indviData[i]);
					 allDataUni.add(indviData[i]);
					 allDataSRTN.add(indviData[i]);
					 allDataRobin.add(indviData[i]);
				 }
			 }
		 }	
	}
	
	public static void FCFS(Scanner filename, Scanner otherFile){
	int runningTime = 0;
	boolean recentTerminate;
	int totalTurnAroundTime=0;
	int totalWaitingTime = 0;
	int[] blockCount;
	int[] runCount;
	int[] TotalCpuTime;
	int[] ArrivalTime;
	int[] B;
	int[] IO;
	int TotalIOTime=0;
	boolean TotalWaiting;
	boolean TotalIOTimeBool;
	String anyThingRunning="";
	boolean called =false;
	boolean forReadyProcess = false;
	int counter=0; // counter for putting data from input into array
	int numOfCycle=0; 
	int numOfCyclePrint=0;
	int done = 0; // to stop program when all processes are done
	boolean isRunning = false;
	ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
	String[] processSituation;
	String initialMessage ="Before Cycle  " +numOfCycle +":  ";


		//for randomNumber
		 Scanner input = null;

	 
		 // putting input data into arrays
		 numOfProcess = Integer.parseInt((String) allData.get(0));
		 ArrivalTime = new int[numOfProcess];
		 B = new int[numOfProcess];
		 IO = new int[numOfProcess];
		 TotalCpuTime = new int[numOfProcess];
		 ForArrivalTime = new int[numOfProcess];
		 ForB = new int[numOfProcess];
		 ForIO = new int[numOfProcess];
		 ForTotalCpuTime = new int[numOfProcess];
		 blockCount = new int[numOfProcess];
		 finishingTime =  new int[numOfProcess];
		 processSituation = new String[numOfProcess];
		 turnaroundTime = new int[numOfProcess];
		 IOTime = new int[numOfProcess];
		 runCount = new int[numOfProcess];
		 waitingTime = new int[numOfProcess];
		 for(int i=0; i<(numOfProcess*4); i+=4) {
			 ArrivalTime[counter] = Integer.parseInt((String) allData.get(i+1));
			 B[counter] = Integer.parseInt((String) allData.get(i+2));
			 TotalCpuTime[counter] = Integer.parseInt((String) allData.get(i+3));
			 IO[counter] = Integer.parseInt((String) allData.get(i+4));
			 ForArrivalTime[counter] = Integer.parseInt((String) allData.get(i+1));
			 ForB[counter] = Integer.parseInt((String) allData.get(i+2));
			 ForTotalCpuTime[counter] = Integer.parseInt((String) allData.get(i+3));
			 ForIO[counter] = Integer.parseInt((String) allData.get(i+4));
			 counter++;
		 }

		 // first few line 
		 int cpuBurst;
		 for(int i=0; i<numOfProcess; i++) {
			 finishingTime[i] = -100;
			 processSituation[i] = "unstarted";
			 initialMessage += processSituation[i] + " " + runCount[i] + " " ; 
		 }

		 if(specific) {
		 System.out.println(initialMessage);
		 System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
		 }
//		 cpuBurst = randomOS(B[(int) readyProcess.get(0)]);
//		 System.out.println("cpuBurst= " + cpuBurst);
//		 allRandomNum.remove(0); 
//		 System.out.println("arrivaltime = " + ArrivalTime[0]);
//		 System.out.println("arrivaltime = " + ArrivalTime[1]);
//		 System.out.println("arrivaltime = " + ArrivalTime[2]);
//		 System.out.println("readyProcess= " + readyProcess.toString());
		 for(int i=0; i<numOfProcess; i++) {
			 if(ArrivalTime[i]==numOfCycle) {
				 if(!isRunning) {
					 processSituation[i]= "running";
					 readyProcess.add(i);
					 isRunning = true;
				 }
				 else if (isRunning){
				 processSituation[i] = "ready";
				 readyProcess.add(i);
				 }
			 }
		 }
		 numOfCycle++;
		 numOfCyclePrint++;
		 runCount[(int) readyProcess.get(0)] = randomOS(B[(int) readyProcess.get(0)]);
		 allRandomNum.remove(0);
		 for(int i=0; i<numOfProcess;i++) {
			 done += TotalCpuTime[i]; 
		 } 
		// starting from here is the while loop
		 while(done!=0) {
			 done=0;
			 recentTerminate = false;
			 called= false;
			 TotalWaiting = false;
			 TotalIOTimeBool = false;
//			 System.out.println("/////////////////////" + TotalCpuTime[0]);
//			 System.out.println("/////////////////////" + TotalCpuTime[1]);
//			 System.out.println("/////////////////////" + readyProcess.toString());
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
				 if(processSituation[i].contains("ready")) {
					 waitingTime[i]++;
				 }
				 if(processSituation[i].contains("blocked")){
					 TotalIOTimeBool = true;
				 }
			 }
			 if(TotalIOTimeBool) {
				 TotalIOTime++;
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was First Come First Served\n");
				 break;
			 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
//					 processSituation[i] = "terminated";
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 initialMessage ="Before Cycle  " +numOfCyclePrint +":  ";
			 for(int i=0; i<numOfProcess; i++) {
				 initialMessage += processSituation[i] + " ";
 				 if(processSituation[i].toString().contains("running")) {
 					initialMessage += runCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("ready")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("blocked")) {
 					 initialMessage += blockCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("terminated")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("unstarted")) {
 					 initialMessage += "0 ";
 				 }
			 }
			 if(specific) {
			 System.out.println(initialMessage);
			 }
//			 System.out.println("/////////// " + readyProcess.toString());
//			 System.out.println("/////////////" + runCount[(int) readyProcess.get(0)]);
			 if(!readyProcess.isEmpty()) {
			 if(runCount[(int) readyProcess.get(0)] > 0) {
			 runCount[(int) readyProcess.get(0)]--;
			 }
			 if(TotalCpuTime[(int) readyProcess.get(0)] > 0) {
				 if(TotalCpuTime[(int) readyProcess.get(0)]-1 ==0) {
					 recentTerminate = true;
				 }
				 TotalCpuTime[(int) readyProcess.get(0)] --;
			 }
			 }
			 for(int i=0; i<numOfProcess; i++) {
				 if(processSituation[i].contains("unstarted") && ArrivalTime[i]==numOfCycle) {
					 processSituation[i] = "ready";
				 }
			 }
			 for(int i=0; i <numOfProcess; i++) {
//				 System.out.println("//////////////////////// " + blockCount[i]);
				 if(blockCount[i]>0) {
					 blockCount[i]--;
				 }
				 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 processSituation[i] = "terminated";

					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 if(!readyProcess.isEmpty() && recentTerminate) {
				 readyProcess.remove(0);
			 }
			 
			 done=0;
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was First Come First Served\n");
				 break;
			 }
//			 System.out.println("////////////// " + readyProcess.get(0));
//			 System.out.println("//////////////" + TotalCpuTime[1]);
//			 System.out.println("aaaaaaaaaaaaaa " + readyProcess.toString());
		 if(!readyProcess.isEmpty() && !recentTerminate) {
//			 System.out.println("runCount= " +  numOfCycle + "  " + runCount[(int) readyProcess.get(0)]);
		 if ( runCount[(int) readyProcess.get(0)] == 0) {
			 if(TotalCpuTime[(int) readyProcess.get(0)]!=0) {
			 	processSituation[(int) readyProcess.get(0)] = "blocked";
				 if(specific) {
			 	System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
				 }
			 	blockCount[(int) readyProcess.get(0)] = randomOS(IO[(int) readyProcess.get(0)]);
			 	IOTime[(int) readyProcess.get(0)] += randomOS(IO[(int) readyProcess.get(0)]);
				allRandomNum.remove(0);
				readyProcess.remove(0);
			 }
		 }
		 }
//		 System.out.println("///////////////// " + allRandomNum.get(0));
//		 System.out.println("////////////////// " + blockCount[0]);
//		 System.out.println("////////////////// " + blockCount[1]);
//		 System.out.println("////////////////// " + blockCount[2]);
//		 System.out.println("aaaaaaaaaaaa " + processSituation[1]);
			 for(int i=0; i<numOfProcess; i++) {
				 if(blockCount[i]==0 && !readyProcess.contains(i)) {
					 if(!processSituation[i].contains("terminated")  && !processSituation[i].contains("unstarted")) {
					 processSituation[i] = "ready";
					 readyProcess.add(i);
					 }
				 }
			 }
//			 System.out.println("////////////////" + readyProcess.toString());
//			 System.out.println("aaaaaaaaaaaaaaa" + processSituation[0]);
//			 System.out.println("bbbbbbbbbbbbbbb" + processSituation[1]);
//			 System.out.println("readyProcess= " +readyProcess.toString());
			 anyThingRunning ="";
			 for(int i=0; i<numOfProcess; i++) {
				 anyThingRunning += processSituation[i];
			 }
//			 System.out.println("//////////////// " + anyThingRunning);
			 if(!anyThingRunning.contains("running") && !readyProcess.isEmpty()) {
				 processSituation[(int) readyProcess.get(0)] = "running";
				 runCount[(int) readyProcess.get(0)] = randomOS(B[(int) readyProcess.get(0)]);
				 if(specific) {
				 System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
				 }
				 allRandomNum.remove(0);
			 }
//			 System.out.println("//////////////////" + processSituation[0]);
//			 System.out.println("aaaaaaaaaaaaaaaaa" + processSituation[1]);
//			 System.out.println("aaaaaaaaaaaaaaaaa" + processSituation[2]);

			 numOfCycle++;
			 numOfCyclePrint++;
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was First Come First Served\n");
				 break;
			 }

		 }
			for(int i=0; i<numOfProcess; i++) {
				runningTime += ForTotalCpuTime[i];
//				TotalIOTime += IOTime[i];
				totalTurnAroundTime += turnaroundTime[i];
				totalWaitingTime += waitingTime[i];
			}
			for(int i=0; i<numOfProcess; i++) {
				System.out.println("Process " + i + ":");
				System.out.println("     (A,B,C,IO) = " +"(" + ForArrivalTime[i] +", " + ForB[i] + ", "+ ForTotalCpuTime[i] +", " + ForIO[i] + ")");
				System.out.println("     Finishing time: " + finishingTime[i]);
				System.out.println("     turnaround time: " + turnaroundTime[i]);
				System.out.println("     I/O time: " + IOTime[i]);
				System.out.println("     waiting time " + waitingTime[i]);
				System.out.println("\n");
				}
			System.out.println("Summary Data:");
			System.out.println("     Finishing time: " + numOfCycle);
			System.out.format("     CPU Utillization: %.6f%n" ,  ((double)runningTime)/(double)(numOfCycle));
			System.out.format("     I/O Utilizaation: %.6f%n" , ((double)(TotalIOTime)/(double)(numOfCycle)));
			System.out.format("     ThroughPut: %.6f processes per hundred cycles%n"  ,(100.0/(double)(numOfCycle) *numOfProcess));
			System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime)/(double)(numOfProcess)));
			System.out.format("     Average waiting time: %.6f%n \n", ((double)(totalWaitingTime)/(double)(numOfProcess)));
		 
} // end of FCFS method
	
	
	
	
	
	
///////////////////////////////////////// uniProcessor	
	public static void Uniprocessor(Scanner filename, Scanner otherFile){
	int runningTime = 0;
	boolean recentTerminate;
	int totalTurnAroundTime=0;
	int totalWaitingTime = 0;
	int[] blockCount;
	int[] runCount;
	int[] TotalCpuTime;
	int[] ArrivalTime;
	int[] B;
	int[] IO;
	int TotalIOTime=0;
	String anyThingRunning="";
	boolean called =false;
	boolean forReadyProcess = false;
	int counter=0; // counter for putting data from input into array
	int numOfCycle=0; 
	int numOfCyclePrint=0;
	int done = 0; // to stop program when all processes are done
	boolean isRunning = false;
	ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
	String[] processSituation;
	String initialMessage ="Before Cycle  " +numOfCycle +":  ";

		String[] indviData = null;
		String[] splitFile = null;
		//for randomNumber
		 Scanner input = null;
		 
		 // putting input data into arrays
		 numOfProcess = Integer.parseInt((String) allDataUni.get(0));
		 ArrivalTime = new int[numOfProcess];
		 B = new int[numOfProcess];
		 IO = new int[numOfProcess];
		 TotalCpuTime = new int[numOfProcess];
		 ForArrivalTime = new int[numOfProcess];
		 ForB = new int[numOfProcess];
		 ForIO = new int[numOfProcess];
		 ForTotalCpuTime = new int[numOfProcess];
		 blockCount = new int[numOfProcess];
		 finishingTime =  new int[numOfProcess];
		 processSituation = new String[numOfProcess];
		 turnaroundTime = new int[numOfProcess];
		 IOTime = new int[numOfProcess];
		 runCount = new int[numOfProcess];
		 waitingTime = new int[numOfProcess];
		 for(int i=0; i<(numOfProcess*4); i+=4) {
			 ArrivalTime[counter] = Integer.parseInt((String) allDataUni.get(i+1));
			 B[counter] = Integer.parseInt((String) allDataUni.get(i+2));
			 TotalCpuTime[counter] = Integer.parseInt((String) allDataUni.get(i+3));
			 IO[counter] = Integer.parseInt((String) allDataUni.get(i+4));
			 ForArrivalTime[counter] = Integer.parseInt((String) allDataUni.get(i+1));
			 ForB[counter] = Integer.parseInt((String) allDataUni.get(i+2));
			 ForTotalCpuTime[counter] = Integer.parseInt((String) allDataUni.get(i+3));
			 ForIO[counter] = Integer.parseInt((String) allDataUni.get(i+4));
			 counter++;
		 }

		 // first few line 
		 int cpuBurst;
		 for(int i=0; i<numOfProcess; i++) {
			 finishingTime[i] = -100;
			 processSituation[i] = "unstarted";
			 initialMessage += processSituation[i] + " " + runCount[i] + " " ; 
		 }

		 if(specific) {
		 System.out.println(initialMessage);
		 System.out.println("Find burst when choosing ready process to run " + allRandomNumUni.get(0));
		 }
//		 cpuBurst = randomOS(B[(int) readyProcess.get(0)]);
//		 System.out.println("cpuBurst= " + cpuBurst);
//		 allRandomNum.remove(0); 
//		 System.out.println("arrivaltime = " + ArrivalTime[0]);
//		 System.out.println("arrivaltime = " + ArrivalTime[1]);
//		 System.out.println("arrivaltime = " + ArrivalTime[2]);
//		 System.out.println("readyProcess= " + readyProcess.toString());
		 for(int i=0; i<numOfProcess; i++) {
			 if(ArrivalTime[i]==numOfCycle) {
				 if(!isRunning) {
					 processSituation[i]= "running";
					 isRunning = true;
				 }
				 else if (isRunning){
				 processSituation[i] = "ready";
				 }
			 }
		 }
		 for( int i=0; i<numOfProcess; i++) {
			 readyProcess.add(i);
		 }
		 numOfCycle++;
		 numOfCyclePrint++;
		 runCount[(int) readyProcess.get(0)] = randomOSUni(B[(int) readyProcess.get(0)]);
		 allRandomNumUni.remove(0);
		 for(int i=0; i<numOfProcess;i++) {
			 done += TotalCpuTime[i]; 
		 } 


		// starting from here is the while loop
		 while(done!=0) {
			 done=0;
			 recentTerminate = false;
			 called= false;
//			 System.out.println("/////////////////////" + TotalCpuTime[0]);
//			 System.out.println("/////////////////////" + TotalCpuTime[1]);
//			 System.out.println("/////////////////////" + readyProcess.toString());
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
				 if(processSituation[i].contains("ready")) {
					 waitingTime[i]++;
				 }
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Uniprocessor\n");
				 break;
			 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 initialMessage ="Before Cycle  " +numOfCyclePrint +":  ";
			 for(int i=0; i<numOfProcess; i++) {
				 initialMessage += processSituation[i] + " ";
 				 if(processSituation[i].toString().contains("running")) {
 					initialMessage += runCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("ready")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("blocked")) {
 					 initialMessage += blockCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("terminated")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("unstarted")) {
 					 initialMessage += "0 ";
 				 }
			 }
			 if(specific) {
			 System.out.println(initialMessage);
			 }
//			 System.out.println("/////////// " + readyProcess.toString());
//			 System.out.println("/////////////" + runCount[(int) readyProcess.get(0)]);
			 if(!readyProcess.isEmpty() && processSituation[(int) readyProcess.get(0)].contains("running")) {
			 if(runCount[(int) readyProcess.get(0)] > 0) {
			 runCount[(int) readyProcess.get(0)]--;
			 }
			 if(TotalCpuTime[(int) readyProcess.get(0)] > 0) {
				 if(TotalCpuTime[(int) readyProcess.get(0)]-1 ==0) {
					 recentTerminate = true;
				 }
				 TotalCpuTime[(int) readyProcess.get(0)] --;
			 }
			 }			 
			 for(int i=0; i<numOfProcess; i++) {
				 if(processSituation[i].contains("unstarted") && ArrivalTime[i]==numOfCycle) {
					 processSituation[i] = "ready";
				 }
			 }
			 for(int i=0; i <numOfProcess; i++) {
//				 System.out.println("//////////////////////// " + blockCount[i]);
				 if(blockCount[i]>0) {
					 blockCount[i]--;
				 }
				 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 processSituation[i] = "terminated";
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 if(!readyProcess.isEmpty() && recentTerminate ) {
				 readyProcess.remove(0);
				 if(!readyProcess.isEmpty()) {
				 processSituation[(int) readyProcess.get(0)] = "running";
				 runCount[(int) readyProcess.get(0)] =randomOSUni(B[(int) readyProcess.get(0)]);
				 allRandomNumUni.remove(0);
				 }
			 }
			 
			 done=0;
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Uniprocessor\n");
				 break;
			 }
//			 System.out.println("////////////// " + readyProcess.get(0));
//			 System.out.println("//////////////" + TotalCpuTime[1]);
//			 System.out.println("aaaaaaaaaaaaaa " + readyProcess.toString());

//		 System.out.println("///////////////// " + allRandomNum.get(0));
//		 System.out.println("////////////////// " + blockCount[0]);
//		 System.out.println("////////////////// " + blockCount[1]);
//		 System.out.println("////////////////// " + blockCount[2]);
//		 System.out.println("aaaaaaaaaaaa " + processSituation[1]);
			 for(int i=0; i<numOfProcess; i++) {
				 if(blockCount[i]==0 && !readyProcess.contains(i)) {
					 if(!processSituation[i].contains("terminated")  && !processSituation[i].contains("unstarted")) {
					 processSituation[i] = "ready";
					 }
				 }
			 }
//			 System.out.println("////////////////" + readyProcess.toString());
//			 System.out.println("aaaaaaaaaaaaaaa" + processSituation[0]);
//			 System.out.println("bbbbbbbbbbbbbbb" + processSituation[1]);
//			 System.out.println("readyProcess= " +readyProcess.toString());
			 anyThingRunning ="";
			 for(int i=0; i<numOfProcess; i++) {
				 anyThingRunning += processSituation[i];
			 }
//			 System.out.println("//////////////// " + anyThingRunning);
			 if(!readyProcess.isEmpty() && blockCount[(int) readyProcess.get(0)]==0 && processSituation[(int) readyProcess.get(0)].contains("blocked")) {
				 processSituation[(int) readyProcess.get(0)] = "running";
				 runCount[(int) readyProcess.get(0)] = randomOSUni(B[(int) readyProcess.get(0)]);
				 if(specific) {
				 System.out.println("Find burst when choosing ready process to run " + allRandomNumUni.get(0));
				 }
				 allRandomNumUni.remove(0);
			 }
//			 System.out.println("//////////////////" + processSituation[0]);
//			 System.out.println("aaaaaaaaaaaaaaaaa" + processSituation[1]);
//			 System.out.println("aaaaaaaaaaaaaaaaa" + processSituation[2]);

			 if(!readyProcess.isEmpty() && !recentTerminate) {
//				 System.out.println("runCount= " +  numOfCycle + "  " + runCount[(int) readyProcess.get(0)]);
			 if ( runCount[(int) readyProcess.get(0)] == 0 && blockCount[(int) readyProcess.get(0)] == 0) {
				 if(TotalCpuTime[(int) readyProcess.get(0)]!=0) {
				 	processSituation[(int) readyProcess.get(0)] = "blocked";
					 if(specific) {
				 	System.out.println("Find I/O burst when blocking a process " + allRandomNumUni.get(0));
					 }
				 	blockCount[(int) readyProcess.get(0)] = randomOSUni(IO[(int) readyProcess.get(0)]);
				 	IOTime[(int) readyProcess.get(0)] += randomOSUni(IO[(int) readyProcess.get(0)]);
					allRandomNumUni.remove(0);
				 }
			 }
			 }
			 
			 
			 numOfCycle++;
			 numOfCyclePrint++;
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Uniprocessor\n");
				 break;
			 }

		 }
			for(int i=0; i<numOfProcess; i++) {
				runningTime += ForTotalCpuTime[i];
				TotalIOTime += IOTime[i];
				totalTurnAroundTime += turnaroundTime[i];
				totalWaitingTime += waitingTime[i];
			}
			for(int i=0; i<numOfProcess; i++) {
				System.out.println("Process " + i + ":");
				System.out.println("     (A,B,C,IO) = " +"(" + ForArrivalTime[i] +", " + ForB[i] + ", "+ ForTotalCpuTime[i] +", " + ForIO[i] + ")");
				System.out.println("     Finishing time: " + finishingTime[i]);
				System.out.println("     turnaround time: " + turnaroundTime[i]);
				System.out.println("     I/O time: " + IOTime[i]);
				System.out.println("     waiting time " + waitingTime[i]);
				System.out.println("\n");
				}

			System.out.println("Summary Data:");
			System.out.println("     Finishing time: " + numOfCycle);
			System.out.format("     CPU Utillization: %.6f%n" ,  ((double)runningTime)/(double)(numOfCycle));
			System.out.format("     I/O Utilizaation: %.6f%n" , ((double)(TotalIOTime)/(double)(numOfCycle)));
			System.out.format("     ThroughPut: %.6f processes per hundred cycles%n"  ,(100.0/(double)(numOfCycle) *numOfProcess));
			System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime)/(double)(numOfProcess)));
			System.out.format("     Average waiting time: %.6f%n \n", ((double)(totalWaitingTime)/(double)(numOfProcess)));
		 
} // end of Uniprogrammed method
	
	
	
public static void PSJF(Scanner filename, Scanner otherFile){
	int runningTime = 0;
	boolean recentTerminate;
	int totalTurnAroundTime=0;
	int totalWaitingTime = 0;
	int[] blockCount;
	int[] runCount;
	int[] TotalCpuTime;
	int[] ArrivalTime;
	int[] B;
	int[] IO;
	int index = 0;
	boolean remaining = false;
	boolean tieBreaker = false;
	int TotalIOTime=0;
	int readyCounter = 0;
	String anyThingRunning="";
	boolean called =false;
	boolean recentReady=false;
	int counter=0; // counter for putting data from input into array
	int numOfCycle=0; 
	int numOfCyclePrint=0;
	boolean justChanged ;
	int done = 0; // to stop program when all processes are done
	boolean isRunning = false;
	ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
	String[] processSituation;
	ArrayList compareList = new ArrayList<String>();
	ArrayList copyList = new ArrayList<String>();
	String initialMessage ="Before Cycle  " +numOfCycle +":  ";

		String[] indviData = null;
		String[] splitFile = null;
		//for randomNumber
		 Scanner input = null;

		while(otherFile.hasNext()) {
			 String line = otherFile.next();
			 splitFile = line.split("\\s+");			
			 for(int i = 0; i < splitFile.length; i++){
				 if(!"".equals(splitFile[i])) {					
					 allRandomNumSRTN.add(splitFile[i]);
				 }
			 }
		}

		 while (filename.hasNext()) 
		 {
			 String currentLine = filename.next();
			 indviData = currentLine.split("\\s+");	
			 for(int i = 0; i < indviData.length; i++){
				 if(!"".equals(indviData[i])) {	
					 allDataSRTN.add(indviData[i]);
				 }
			 }
		 }		 
		 // putting input data into arrays
		 numOfProcess = Integer.parseInt((String) allDataSRTN.get(0));
		 ArrivalTime = new int[numOfProcess];
		 B = new int[numOfProcess];
		 IO = new int[numOfProcess];
		 TotalCpuTime = new int[numOfProcess];
		 ForArrivalTime = new int[numOfProcess];
		 ForB = new int[numOfProcess];
		 ForIO = new int[numOfProcess];
		 ForTotalCpuTime = new int[numOfProcess];
		 blockCount = new int[numOfProcess];
		 finishingTime =  new int[numOfProcess];
		 processSituation = new String[numOfProcess];
		 turnaroundTime = new int[numOfProcess];
		 IOTime = new int[numOfProcess];
		 runCount = new int[numOfProcess];
		 waitingTime = new int[numOfProcess];
		 for(int i=0; i<(numOfProcess*4); i+=4) {
			 ArrivalTime[counter] = Integer.parseInt((String) allDataSRTN.get(i+1));
			 B[counter] = Integer.parseInt((String) allDataSRTN.get(i+2));
			 TotalCpuTime[counter] = Integer.parseInt((String) allDataSRTN.get(i+3));
			 IO[counter] = Integer.parseInt((String) allDataSRTN.get(i+4));
			 ForArrivalTime[counter] = Integer.parseInt((String) allDataSRTN.get(i+1));
			 ForB[counter] = Integer.parseInt((String) allDataSRTN.get(i+2));
			 ForTotalCpuTime[counter] = Integer.parseInt((String) allDataSRTN.get(i+3));
			 ForIO[counter] = Integer.parseInt((String) allDataSRTN.get(i+4));
			 counter++;
		 }

		 
		 
		 
		 // first few line 
		 int cpuBurst;
		 for(int i=0; i<numOfProcess; i++) {
			 finishingTime[i] = -100;
			 processSituation[i] = "unstarted";
			 initialMessage += processSituation[i] + " " + runCount[i] + " " ; 
		 }

		 if(specific) {
		 System.out.println(initialMessage);
		 System.out.println("Find burst when choosing ready process to run " + allRandomNumSRTN.get(0));
		 }
//		 cpuBurst = randomOS(B[(int) readyProcess.get(0)]);
//		 System.out.println("cpuBurst= " + cpuBurst);
//		 allRandomNumSRTN.remove(0); 
//		 System.out.println("arrivaltime = " + ArrivalTime[0]);
//		 System.out.println("arrivaltime = " + ArrivalTime[1]);
//		 System.out.println("arrivaltime = " + ArrivalTime[2]);
//		 System.out.println("readyProcess= " + readyProcess.toString());
		 for(int i=0; i<numOfProcess; i++) {
			 if(ArrivalTime[i]==numOfCycle) {
				 if(!isRunning) {
					 processSituation[i] = "ready";
					 readyProcess.add(i);
					 isRunning = true;
				 }
				 else if (isRunning){
				 processSituation[i] = "ready";
					for(int j=0; j< readyProcess.size(); j++) {
						if(TotalCpuTime[i] < TotalCpuTime[(int) readyProcess.get(j)]) {
							if(!readyProcess.contains(i)) {
								readyProcess.add(j, i);
							}
						}
						else {
							if(!readyProcess.contains(i)) {
								readyProcess.add(i);
							}
						}
					}
				}
		 }
			 }
		processSituation[(int)readyProcess.get(0)] = "running";
		 numOfCycle++;
		 numOfCyclePrint++;
		 runCount[(int) readyProcess.get(0)] = randomOSSRTN(B[(int) readyProcess.get(0)]);
		 allRandomNumSRTN.remove(0);
		 for(int i=0; i<numOfProcess;i++) {
			 done += TotalCpuTime[i]; 
		 } 
		 

		 
		 
		// starting from here is the while loop
		 boolean TotalIOTimeBool;
		while(done!=0) {
			 done=0;
			 readyCounter =0;
			 recentReady = false;
			 recentTerminate = false;
			 called= false;
			 TotalIOTimeBool = false;
			 justChanged = false;
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
				 if(processSituation[i].contains("ready")) {
					 waitingTime[i]++;
				 }
			 if(processSituation[i].contains("blocked")){
				 TotalIOTimeBool = true;
			 }
			 }
		 if(TotalIOTimeBool) {
			 TotalIOTime++;
		 }
			 

			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
				 break;
			 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 initialMessage ="Before Cycle  " +numOfCyclePrint +":  ";
			 for(int i=0; i<numOfProcess; i++) {
				 initialMessage += processSituation[i] + " ";
 				 if(processSituation[i].toString().contains("running")) {
 					initialMessage += runCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("ready")) {
 					 initialMessage += runCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("blocked")) {
 					 initialMessage += blockCount[i];
 				 }
 				 else if(processSituation[i].toString().contains("terminated")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("unstarted")) {
 					 initialMessage += "0 ";
 				 }
			 }
			 if(specific) {
			 System.out.println(initialMessage);
			 }

			 if(!readyProcess.isEmpty()) {
			 if(runCount[(int) readyProcess.get(0)] > 0) {
			 runCount[(int) readyProcess.get(0)]--;
			 }
			 if(TotalCpuTime[(int) readyProcess.get(0)] > 0) {
				 if(TotalCpuTime[(int) readyProcess.get(0)]-1 ==0) {
					 recentTerminate = true;
				 }
				 TotalCpuTime[(int) readyProcess.get(0)] --;
				 if(recentTerminate) {
					 processSituation[(int) readyProcess.get(0)] = "terminated";
					 readyProcess.remove(0);
				 }
			 }
			 }
			 for(int i=0; i<numOfProcess; i++) {
				 if(processSituation[i].contains("unstarted") && ArrivalTime[i]==numOfCycle) {
					 processSituation[i] = "ready";
				 }
			 }
			 for(int i=0; i <numOfProcess; i++) {
//				 System.out.println("//////////////////////// " + blockCount[i]);
				 if(blockCount[i]>0) {
					 if(blockCount[i]-1==0) {
						 justChanged = true;
						 index = i;
					 }
					 blockCount[i]--;
				 }
				 }
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 processSituation[i] = "terminated";
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }

			 if(!readyProcess.isEmpty() && recentTerminate) {
				 readyProcess.remove(0);
			 }			 
			 done=0;
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
				 break;
			 }


		 // block
			 int initalIndex=-1;
			 int smallestOne=0;
		 if(!readyProcess.isEmpty() && !recentTerminate) {
		 if ( runCount[(int) readyProcess.get(0)] == 0) {
			 if(TotalCpuTime[(int) readyProcess.get(0)]!=0) {
			 	processSituation[(int) readyProcess.get(0)] = "blocked";
				 if(specific) {
			 	System.out.println("Find I/O burst when blocking a process " + allRandomNumSRTN.get(0));
				 }
			 	blockCount[(int) readyProcess.get(0)] = randomOSSRTN(IO[(int) readyProcess.get(0)]);
			 	IOTime[(int) readyProcess.get(0)] += randomOSSRTN(IO[(int) readyProcess.get(0)]);
				allRandomNumSRTN.remove(0);
				readyProcess.remove(0);
			 }
		 }
		 }
		 ///////////////////////////end of blocking

		 
		 
		 
		 //ready
		 for( int i=0; i< numOfProcess; i++) {
			 if(blockCount[i]==0) {
				 if(!processSituation[i].contains("terminated")  && !processSituation[i].contains("unstarted")) {
					 processSituation[i] = "ready";
				 }
			 }
		 }


			 for(int i=0; i<numOfProcess; i++) {
				 	if(!processSituation[i].contains("terminated") && !processSituation[i].contains("unstarted")) {
				 		if(blockCount[i] == 0) {
				 			if(!readyProcess.contains(i))
				 			readyProcess.add(i);
				 		}
				 	} //////////////////////////////////////////////end of ready
			 }

			// sort readyList before choosing to run;
				 	if(!readyProcess.isEmpty()) {
				 	compareList.add(readyProcess.get(0));
				 	}
			for(int i=0; i<readyProcess.size(); i++) {
				for(int j=0; j<compareList.size(); j++) {
						if(TotalCpuTime[(int) readyProcess.get(i)] < TotalCpuTime[(int) compareList.get(j)]) {
							if(!compareList.contains(readyProcess.get(i))) {
							compareList.add(j, readyProcess.get(i));
							}
						}
						else if(TotalCpuTime[(int) readyProcess.get(i)] > TotalCpuTime[(int) compareList.get(j)]){
							if(!compareList.contains(readyProcess.get(i))) {
							compareList.add(j+1, readyProcess.get(i));
							}
						}
						else {
								if(ArrivalTime[ (int)readyProcess.get(i)] < ArrivalTime[ (int)readyProcess.get(j)]) {
									if(!compareList.contains(readyProcess.get(i))) {
									compareList.add(j, readyProcess.get(i));
									}
								}
								else if(ArrivalTime[ (int)readyProcess.get(i)] > ArrivalTime[ (int)readyProcess.get(j)]) {
									if(!compareList.contains(readyProcess.get(i))) {
									compareList.add(j+1, readyProcess.get(i));
									}
								}
								else {
									if((int)readyProcess.get(i) < (int)readyProcess.get(j)) {
										if(!compareList.contains(readyProcess.get(i))) {
										compareList.add(j, readyProcess.get(i));
										}
									}
									else {
										if(!compareList.contains(readyProcess.get(i))) {
										compareList.add(j+1, readyProcess.get(i));
										}
									}
								}
						}
						}
					}
			readyProcess.clear();
			for(int i=0; i<compareList.size(); i++) {
				readyProcess.add(i, compareList.get(i));
			}
		 	compareList.clear();
			
			 anyThingRunning ="";
			 for(int i=0; i<numOfProcess; i++) {
			 anyThingRunning += processSituation[i];
		 }
		 	




			 copyList.clear();
			 for( int i=0; i<readyProcess.size(); i++) {
					copyList.add(i, readyProcess.get(i));
			 }

			 
			 for(int i=0; i<readyProcess.size(); i++) {
				 if(processSituation[(int) readyProcess.get(i)].contains("terminated")) {
					 copyList.remove(readyProcess.get(i));
					 }
				 }
			 

			 
				readyProcess.clear();
				for(int i=0; i<copyList.size(); i++) {
					readyProcess.add(i, copyList.get(i));
				}
				
				

			 anyThingRunning = "";
			 for(int i=0; i<numOfProcess; i++) {
				 anyThingRunning += processSituation[i];
			 }
			 
			 
			 
			 

			 if(!readyProcess.isEmpty()) {
			for( int i=0; i < numOfProcess; i++) {
			 if(processSituation[(int) readyProcess.get(0)].contains("ready") && runCount[(int) readyProcess.get(0)]!=0) {
				 processSituation[(int) readyProcess.get(0)] = "running";
			 }
			}
			 if (processSituation[(int) readyProcess.get(0)].contains("ready") && runCount[(int) readyProcess.get(0)]==0) {
			 if(!anyThingRunning.contains("running") && !readyProcess.isEmpty()) {
				 processSituation[(int) readyProcess.get(0)] = "running";
				 runCount[(int) readyProcess.get(0)] = randomOSSRTN(B[(int) readyProcess.get(0)]);
				 if(specific) {
				 System.out.println("Find burst when choosing ready process to run " + allRandomNumSRTN.get(0));
				 }
				 allRandomNumSRTN.remove(0);
			 }
			 }
			 }


			 numOfCycle++;
			 numOfCyclePrint++;
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Preemptive Shortest Job First\n");
				 break;
			 }

		 }


			for(int i=0; i<numOfProcess; i++) {
				runningTime += ForTotalCpuTime[i];
				totalTurnAroundTime += turnaroundTime[i];
				totalWaitingTime += waitingTime[i];
			}
			for(int i=0; i<numOfProcess; i++) {
				System.out.println("Process " + i + ":");
				System.out.println("     (A,B,C,IO) = " +"(" + ForArrivalTime[i] +", " + ForB[i] + ", "+ ForTotalCpuTime[i] +", " + ForIO[i] + ")");
				System.out.println("     Finishing time: " + finishingTime[i]);
				System.out.println("     turnaround time: " + turnaroundTime[i]);
				System.out.println("     I/O time: " + IOTime[i]);
				System.out.println("     waiting time " + waitingTime[i]);
				System.out.println("\n");
				}

			System.out.println("Summary Data:");
			System.out.println("     Finishing time: " + numOfCycle);
			System.out.format("     CPU Utillization: %.6f%n" ,  ((double)runningTime)/(double)(numOfCycle));
			System.out.format("     I/O Utilizaation: %.6f%n" , ((double)(TotalIOTime)/(double)(numOfCycle)));
			System.out.format("     ThroughPut: %.6f processes per hundred cycles%n"  ,(100.0/(double)(numOfCycle) *numOfProcess));
			System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime)/(double)(numOfProcess)));
			System.out.format("     Average waiting time: %.6f%n \n", ((double)(totalWaitingTime)/(double)(numOfProcess)));
 
} // end of SRTN method
	

	public static void RoundRobin(Scanner filename, Scanner otherFile){
	int runningTime = 0;
	boolean recentTerminate;
	int totalTurnAroundTime=0;
	int totalWaitingTime = 0;
	int reaminder;
	int[] blockCount;
	int[] runCount;
	int[] TotalCpuTime;
	int[] ArrivalTime;
	int[] B;
	int[] IO;
	int[] remainder;
	int TotalIOTime=0;
	String anyThingRunning="";
	boolean called =false;
	boolean forReadyProcess = false;
	int counter=0; // counter for putting data from input into array
	int numOfCycle=0; 
	int numOfCyclePrint=0;
	int done = 0; // to stop program when all processes are done
	boolean isRunning = false;
	ArrayList readyProcess = new ArrayList(); // tells program what process is next  to run in list
	String[] processSituation;
	String initialMessage ="Before Cycle  " +numOfCycle +":  ";

		String[] indviData = null;
		String[] splitFile = null;
		//for randomNumber
		 Scanner input = null;

		while(otherFile.hasNext()) {
			 String line = otherFile.next();
			 splitFile = line.split("\\s+");			
			 for(int i = 0; i < splitFile.length; i++){
				 if(!"".equals(splitFile[i])) {					
					 allRandomNumRobin.add(splitFile[i]);
				 }
			 }
		}

		 while (filename.hasNext()) 
		 {
			 String currentLine = filename.next();
			 indviData = currentLine.split("\\s+");	
			 for(int i = 0; i < indviData.length; i++){
				 if(!"".equals(indviData[i])) {	
					 allDataRobin.add(indviData[i]);
				 }
			 }
		 }		 
		 // putting input data into arrays
		 numOfProcess = Integer.parseInt((String) allDataRobin.get(0));
		 ArrivalTime = new int[numOfProcess];
		 remainder = new int[numOfProcess];
		 B = new int[numOfProcess];
		 IO = new int[numOfProcess];
		 TotalCpuTime = new int[numOfProcess];
		 ForArrivalTime = new int[numOfProcess];
		 ForB = new int[numOfProcess];
		 ForIO = new int[numOfProcess];
		 ForTotalCpuTime = new int[numOfProcess];
		 blockCount = new int[numOfProcess];
		 finishingTime =  new int[numOfProcess];
		 processSituation = new String[numOfProcess];
		 turnaroundTime = new int[numOfProcess];
		 IOTime = new int[numOfProcess];
		 runCount = new int[numOfProcess];
		 waitingTime = new int[numOfProcess];
		 for(int i=0; i<(numOfProcess*4); i+=4) {
			 ArrivalTime[counter] = Integer.parseInt((String) allDataRobin.get(i+1));
			 B[counter] = Integer.parseInt((String) allDataRobin.get(i+2));
			 TotalCpuTime[counter] = Integer.parseInt((String) allDataRobin.get(i+3));
			 IO[counter] = Integer.parseInt((String) allDataRobin.get(i+4));
			 ForArrivalTime[counter] = Integer.parseInt((String) allDataRobin.get(i+1));
			 ForB[counter] = Integer.parseInt((String) allDataRobin.get(i+2));
			 ForTotalCpuTime[counter] = Integer.parseInt((String) allDataRobin.get(i+3));
			 ForIO[counter] = Integer.parseInt((String) allDataRobin.get(i+4));
			 counter++;
		 }

		 // first few line 
		 int cpuBurst;
		 for(int i=0; i<numOfProcess; i++) {
			 processSituation[i] = "unstarted";
			 initialMessage += processSituation[i] + " " + runCount[i] + " " ; 
		 }

		 if(specific) {
		 System.out.println(initialMessage);
		 System.out.println("Find burst when choosing ready process to run " + allRandomNumRobin.get(0));
		 }

		 for(int i=0; i<numOfProcess; i++) {
			 if(ArrivalTime[i]==numOfCycle) {
				 if(!isRunning) {
					 processSituation[i]= "ready";
					 readyProcess.add(i);
					 if(randomOSRobin(B[(int) readyProcess.get(0)])>2) {
						 processSituation[(int) readyProcess.get(0)] = "Cready";
						 remainder[(int) readyProcess.get(0)] = randomOSRobin(B[(int) readyProcess.get(0)])-2;
					 }
					 isRunning = true;
				 }
				 else if (isRunning){
				 processSituation[i] = "ready";
				 readyProcess.add(i);
				 }
			 }
		 }
		 

		 if(processSituation[(int)readyProcess.get(0)].equals("ready")) {
		 runCount[(int) readyProcess.get(0)] = randomOSRobin(B[(int) readyProcess.get(0)]);
		 processSituation[(int)readyProcess.get(0)] = "running";
		 }
		 if(processSituation[(int)readyProcess.get(0)].contains("Cready")) {
			 processSituation[(int)readyProcess.get(0)] = "Crunning";
			 runCount[(int) readyProcess.get(0)] = 2; 
		 }
		 allRandomNumRobin.remove(0);
		 for(int i=0; i<numOfProcess;i++) {
			 done += TotalCpuTime[i]; 
		 } 
		 
		 numOfCycle++;
		 numOfCyclePrint++;

		 
		 
		// starting from here is the while loop
		 while(done!=0) {
			 done=0;
			 int readyCounter = 0;
			 boolean recentReady = false;
			 recentTerminate = false;
			 called= false;
			 boolean TotalIOTimeBool = false;
			 boolean justChanged = false;
			 ArrayList allReady = new ArrayList<String>();
			 for(int i=0; i<numOfProcess;i++) {
				 done += TotalCpuTime[i]; 
				 if(processSituation[i].contains("ready")) {
					 waitingTime[i]++;
				 }
			 if(processSituation[i].contains("blocked")){
				 TotalIOTimeBool = true;
			 }
			 }
		 if(TotalIOTimeBool) {
			 TotalIOTime++;
		 }

		 
		 done=0;
		 for ( int i=0; i<numOfProcess; i++) {
			 done+= TotalCpuTime[i];
		 }
		 if(done==0) {
			 System.out.println("The scheduling algorithm used was Round Robbin \n");
			 break;
		 }
		 
			 for(int i=0; i< numOfProcess; i ++) {
				 if(TotalCpuTime[i] == 0) {
					 if(finishingTime[i] == -100) {
					 finishingTime[i] = numOfCycle;
					 turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
					 }
				 }
			 }
			 initialMessage ="Before Cycle  " +numOfCyclePrint +":  ";
			 for(int i=0; i<numOfProcess; i++) {
				 if(processSituation[i].contains("Cready")) {
					 initialMessage += "ready" + " ";
				 }
				 else if(processSituation[i].contains("Crunning")) {
					 initialMessage += "running" + " ";
				 }
				 else {
				 initialMessage += processSituation[i] + " ";
				 }
 				 if(processSituation[i].toString().contains("running")) {
 					initialMessage += runCount[i] + " ";
 				 }
 				 else if(processSituation[i].toString().contains("ready")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("blocked")) {
 					 initialMessage += blockCount[i] + " ";
 				 }
 				 else if(processSituation[i].toString().contains("terminated")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("unstarted")) {
 					 initialMessage += "0 ";
 				 }
 				 else if(processSituation[i].toString().contains("Crunning")) {
 					 initialMessage += runCount[i] + " ";
 				 }
 				 else if(processSituation[i].toString().contains("Arunning")) {
 					 initialMessage += runCount[i] + " ";
 				 }
 				 else if(processSituation[i].toString().contains("Cready")) {
 					 initialMessage += "0 ";
 				 }
			 }
	
			 if(specific) {
			 System.out.println(initialMessage);
			 }
			 allReady.clear();
			 // runCount
			 if(!readyProcess.isEmpty()) {
			 if(runCount[(int) readyProcess.get(0)] > 0) {
				 if(runCount[(int) readyProcess.get(0)]-1==0) {
					 boolean recentBlock = true;
					 if(processSituation[(int) readyProcess.get(0)].contains("Crunning") && remainder[(int) readyProcess.get(0)]>0) {
						 allReady.add(readyProcess.get(0));
					 }
				 }
			 runCount[(int) readyProcess.get(0)]--;
			 }
			 
			 for( int i=0; i<readyProcess.size(); i++) {
				 if(processSituation[(int) readyProcess.get(i)].contains("terminated")){
					 readyProcess.remove(i);
				 }
			 }
			 // totalCpuTime Count
			 if(TotalCpuTime[(int) readyProcess.get(0)] > 0) {
				 if(TotalCpuTime[(int) readyProcess.get(0)]-1 ==0) {
					 recentTerminate = true;
				 }
				 TotalCpuTime[(int) readyProcess.get(0)] --;
				 if(recentTerminate) {
					 
					 processSituation[(int) readyProcess.get(0)] = "terminated";
					 finishingTime[(int) readyProcess.get(0)]= numOfCycle;
					readyProcess.remove(0);
				 }
			 }
			 }
			 
			 //  unstarted
			 for(int i=0; i<numOfProcess; i++) {
				 if(processSituation[i].contains("unstarted") && ArrivalTime[i]==numOfCycle) {
					 allReady.add(i);
					 processSituation[i] = "ready";
					 readyProcess.add(i);
				 }
			 }
			 
			 //block count
			 for(int i=0; i <numOfProcess; i++) {
//				 System.out.println("//////////////////////// " + blockCount[i]);
				 if(blockCount[i]>0) {
					 blockCount[i]--;
					 if(blockCount[i]==0) {
						 allReady.add(i);
					 }
				 }
				 }
//			 for(int i=0; i <numOfProcess; i++) {
//				 if(processSituation[i].contains("Crunning")) {
//					 if(remainder[i]>0) {
//						 remainder[i]--;
//					 }
//				 }
//			 }

			 done=0;
			 for ( int i=0; i<numOfProcess; i++) {
				 done+= TotalCpuTime[i];
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Round Robbin \n");
				 break;
			 }
			 

//			 if(!readyProcess.isEmpty() && recentTerminate) {
//				 readyProcess.remove(0);
//			 }
//			 if(!readyProcess.isEmpty()) {
//				 if(runCount[(int) readyProcess.get(0)]==0 && processSituation[(int) readyProcess.get(0)].contains("Crunning")) {
//				 
//				 }
//			 }
			 			 
			 
			 //from run to block
			 if(!readyProcess.isEmpty()) {
			 if(runCount[(int) readyProcess.get(0)]==0) {
				 if( !processSituation[(int) readyProcess.get(0)].contains("terminated") || !processSituation[(int) readyProcess.get(0)].contains("unstarted") ||!processSituation[(int) readyProcess.get(0)].contains("blocked")) {
				 if(processSituation[(int) readyProcess.get(0)].contains("Crunning")) {
					 if(remainder[(int) readyProcess.get(0)]>0) {
					 processSituation[(int) readyProcess.get(0)] = "Cready";
					 readyProcess.add(readyProcess.get(0));
					 readyProcess.remove(0);
					 }
					 else if( remainder[(int) readyProcess.get(0)]==0)
					 {
						 processSituation[(int) readyProcess.get(0)] = "blocked";
						 blockCount[(int) readyProcess.get(0)] = randomOSRobin(IO[(int) readyProcess.get(0)]);
						 IOTime[(int) readyProcess.get(0)] += randomOSRobin(IO[(int) readyProcess.get(0)]);
						 if(specific) {
						 System.out.println("Find I/O burst when blocking a process " + allRandomNumRobin.get(0));
						 }
						 readyProcess.remove(0);
						 allRandomNumRobin.remove(0); 
						 }
					 }
				 else if(processSituation[(int) readyProcess.get(0)].contains("running")) {
						 processSituation[(int) readyProcess.get(0)] = "blocked";
						 blockCount[(int) readyProcess.get(0)] = randomOSRobin(IO[(int) readyProcess.get(0)]);
						 IOTime[(int) readyProcess.get(0)] += randomOSRobin(IO[(int) readyProcess.get(0)]);
						 if(specific) {
						 System.out.println("Find I/O burst when blocking a process " + allRandomNumRobin.get(0));
						 }
						 readyProcess.remove(0);
						 allRandomNumRobin.remove(0);
					 }
				 }
			 }
			 }
			 
//			 System.out.println(allReady.toString());
//			 System.out.println()
			 
			 //block to ready
			 if(!readyProcess.isEmpty()) {
			 for( int i=0; i<numOfProcess; i++) {
			 if(blockCount[i] ==0){
				 if(processSituation[i].contains("blocked")) {
					 processSituation[i] = "ready";
					 readyProcess.add(i);
				 }
				 }
		 }
		 }
			 else if(readyProcess.isEmpty()) {
				 for( int i=0; i<numOfProcess; i++) {
					 if(blockCount[i] ==0){
						 if(processSituation[i].contains("blocked")) {
							 processSituation[i] = "ready";
							 readyProcess.add(i);
						 }
						 }
				 }
			 }
			 
			 //tie breaker

			 int index=0;
			 if(allReady.size()>1) {
				 for( int i=0; i<allReady.size(); i++) {
					 for( int j=i+1; j<allReady.size(); j++) {
						 if (ArrivalTime[(int) allReady.get(i)] > ArrivalTime[(int) allReady.get(j)]) {
							 index = (int) allReady.get(i);
							 allReady.set(i, allReady.get(j));
							 allReady.set(j, index);
						 }
						 if (ArrivalTime[(int) allReady.get(i)] == ArrivalTime[(int) allReady.get(j)]) {
							 if( (int) allReady.get(i) > (int) allReady.get(j)) {
								 index = (int) allReady.get(i);
								 allReady.set(i, allReady.get(j));
								 allReady.set(j, index);
							 }
						 }
					 }
				}
			 }
			 for( int i=0; i<allReady.size(); i++) {
				 readyProcess.remove(allReady.get(i));
				 readyProcess.add(allReady.get(i));
			 }
			 


			 //ready to run
			 
			 
			 if(!readyProcess.isEmpty()) {
				 if(processSituation[(int) readyProcess.get(0)].contains("Cready")) {
					 if(remainder[(int) readyProcess.get(0)]>0) {
						 processSituation[(int) readyProcess.get(0)] = "Crunning";
						 if(remainder[(int) readyProcess.get(0)]>2) {
						 runCount[(int) readyProcess.get(0)] = 2;
						 remainder[(int) readyProcess.get(0)]-=2;
						 }
						 else {
							 runCount[(int) readyProcess.get(0)] = remainder[(int) readyProcess.get(0)];
							 remainder[(int) readyProcess.get(0)] =0;
						 }
					 }
					 else {
						 processSituation[(int) readyProcess.get(0)] = "running";
						 
					 }
				 }
				 else if(processSituation[(int) readyProcess.get(0)].contains("ready")) {
				  if(remainder[(int) readyProcess.get(0)]==0) {
//					 System.out.println(randomOSRobin(B[(int) readyProcess.get(0)]));
					 if(randomOSRobin(B[(int) readyProcess.get(0)])>2) {
						 if(specific) {
							 System.out.println("Find burst when choosing ready Process to run " + allRandomNumRobin.get(0));
						 }
						 processSituation[(int) readyProcess.get(0)] = "Crunning";
						 runCount[(int) readyProcess.get(0)] = 2;
						 remainder[(int) readyProcess.get(0)] = randomOSRobin(B[(int) readyProcess.get(0)])-2;
							allRandomNumRobin.remove(0);
					 }
					 else {
						 processSituation[(int) readyProcess.get(0)] = "running";
						 if(specific) {
							 System.out.println("Find burst when choosing ready Process to run " + allRandomNumRobin.get(0));
						 }
						 runCount[(int) readyProcess.get(0)] = randomOSRobin(B[(int) readyProcess.get(0)]);
						 allRandomNumRobin.remove(0);
					 }
				 }
			 }
			 }
			 done=0;
			 for ( int i=0; i<numOfProcess; i++) {
				 done+= TotalCpuTime[i];
			 }
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was Round Robbin \n");
				 break;
			 }
			 numOfCycle++;
			 numOfCyclePrint++;	 
		 }
		 

			for(int i=0; i<numOfProcess; i++) {
				runningTime += ForTotalCpuTime[i];
//				TotalIOTime += IOTime[i];
//				totalTurnAroundTime += turnaroundTime[i];
				totalWaitingTime += waitingTime[i];
//				TotalIOTime +=IO[i];
			}
			for(int i=0; i<numOfProcess; i++) {
				turnaroundTime[i] = finishingTime[i] - ArrivalTime[i];
			}
			for(int i=0; i<numOfProcess; i++) {
				System.out.println("Process " + i + ":");
				System.out.println("     (A,B,C,IO) = " +"(" + ForArrivalTime[i] +", " + ForB[i] + ", "+ ForTotalCpuTime[i] +", " + ForIO[i] + ")");
				System.out.println("     Finishing time: " + finishingTime[i]);
				System.out.println("     turnaround time: " + turnaroundTime[i]);
				totalTurnAroundTime += turnaroundTime[i];
				System.out.println("     I/O time: " + IOTime[i]);
				System.out.println("     waiting time " + waitingTime[i]);
				System.out.println("\n");
				}
			System.out.println("Summary Data:");
			System.out.println("     Finishing time: " + numOfCycle);
			System.out.format("     CPU Utillization: %.6f%n" ,  ((double)runningTime)/(double)(numOfCycle));
			System.out.format("     I/O Utilizaation: %.6f%n" , ((double)(TotalIOTime)/(double)(numOfCycle)));
			System.out.format("     ThroughPut: %.6f processes per hundred cycles%n"  ,(100.0/(double)(numOfCycle) *numOfProcess));
			System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime)/(double)(numOfProcess)));
			System.out.format("     Average waiting time: %.6f%n", ((double)(totalWaitingTime)/(double)(numOfProcess)));

} // end of RoundRobin method
	
	
	
	public static int randomOS (int U) {
		 return  1+(Integer.parseInt((String) allRandomNum.get(randomNumCounter)) % U); 
		}	
	

	public static int randomOSUni (int U) {
		 return  1+(Integer.parseInt((String) allRandomNumUni.get(randomNumCounter)) % U); 
		}	
	public static int randomOSSRTN (int U) {
		 return  1+(Integer.parseInt((String) allRandomNumSRTN.get(randomNumCounter)) % U); 
		}	
	
	public static int randomOSRobin (int U) {
		 return  1+(Integer.parseInt((String) allRandomNumRobin.get(randomNumCounter)) % U); 
		}	
	public static void main(String[] args) throws IOException {
		String filename;
		String randomNumFile;
		filename = args[0];
		if(args[1].contains("--verbose")) {
			specific = true;
			randomNumFile = args[2];
		}
		else {
			randomNumFile = args[1];
		}
		
		Scanner input = newScanner(filename);
		Scanner otherInput = newScanner(randomNumFile);
		Scanner inputRR = newScanner(filename);
		Scanner otherInputRR = newScanner(randomNumFile);
		Scanner inputUni = newScanner(filename);
		Scanner otherInputUni = newScanner(randomNumFile);
		Scanner inputSRTN = newScanner(filename);
		Scanner otherInputSRTN = newScanner(randomNumFile);
		Scanner inputRobin = newScanner(filename);
		Scanner otherInputRobin = newScanner(randomNumFile);
		DataInitiate(input, otherInput);
		FCFS(input, otherInput);
		Uniprocessor(inputUni, otherInputUni);
		PSJF(inputSRTN, otherInputSRTN);
		RoundRobin(inputRobin,otherInputRobin);
		
		

	}


	



}
