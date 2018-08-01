package lab2;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UniProgrammed {

	static ArrayList allRandomNum = new ArrayList<String>();
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
	ArrayList allData = new ArrayList<String>();
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
					 allRandomNum.add(splitFile[i]);
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
				 }
			 }
		 }		 
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
				 runCount[(int) readyProcess.get(0)] =randomOS(B[(int) readyProcess.get(0)]);
				 allRandomNum.remove(0);
				 }
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
				 runCount[(int) readyProcess.get(0)] = randomOS(B[(int) readyProcess.get(0)]);
				 if(specific) {
				 System.out.println("Find burst when choosing ready process to run " + allRandomNum.get(0));
				 }
				 allRandomNum.remove(0);
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
				 	System.out.println("Find I/O burst when blocking a process " + allRandomNum.get(0));
					 }
				 	blockCount[(int) readyProcess.get(0)] = randomOS(IO[(int) readyProcess.get(0)]);
				 	IOTime[(int) readyProcess.get(0)] += randomOS(IO[(int) readyProcess.get(0)]);
					allRandomNum.remove(0);
				 }
			 }
			 }
			 
			 
			 numOfCycle++;
			 numOfCyclePrint++;
			 if(done==0) {
				 System.out.println("The scheduling algorithm used was First Come First Served\n");
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
				}
			System.out.println("Summary Data:");
			System.out.println("     Finishing time: " + numOfCycle);
			System.out.format("     CPU Utillization: %.6f%n" ,  ((double)runningTime)/(double)(numOfCycle));
			System.out.format("     I/O Utilizaation: %.6f%n" , ((double)(TotalIOTime)/(double)(numOfCycle)));
			System.out.format("     ThroughPut: %.6f processes per hundred cycles%n"  ,(100.0/(double)(numOfCycle) *numOfProcess));
			System.out.format("     Average turnaround time: %.6f%n", ((double)(totalTurnAroundTime)/(double)(numOfProcess)));
			System.out.format("     Average waiting time: %.6f%n", ((double)(totalWaitingTime)/(double)(numOfProcess)));
		 
} // end of FCFS method
	
	
	
	
	
	public static int randomOS (int U) {
		 return  1+(Integer.parseInt((String) allRandomNum.get(randomNumCounter)) % U); 
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
		FCFS(input, otherInput);
		
		
		

	}


	



}