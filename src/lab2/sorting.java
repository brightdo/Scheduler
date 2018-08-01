package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class sorting {

	static ArrayList allRandomNum = new ArrayList<String>();
	static ArrayList allRandomNumUni = new ArrayList<String>();
	static ArrayList allRandomNumSRTN = new ArrayList<String>();
	static ArrayList allData = new ArrayList<String>();
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
	public static void sorting(Scanner filename, Scanner otherFile){
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
					 allRandomNumUni.add(splitFile[i]);
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
					 allData.add(indviData[i]);
					 allDataUni.add(indviData[i]);
					 allDataSRTN.add(indviData[i]);
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
		 for( int i=0; i<numOfProcess; i++) {
			 System.out.println(" process " + i);
		 System.out.println(ArrivalTime[i]);
		 System.out.println(B[i]);
		 System.out.println(TotalCpuTime[i]);
		 System.out.println(IO[i]);
		 System.out.println("///////////////////////////");
	}

		 //the whole sorting

		 for( int i=numOfProcess; i<0; i--) {
			 System.out.println("1");
			 for(int j=numOfProcess-1; j<0; j--) {
				 System.out.println("2");
				 if(ArrivalTime[i] < ArrivalTime[j]) {
					 System.out.println("3");
					 ForArrivalTime[0] = ArrivalTime[j];
					 ForB[0] = ArrivalTime[j];
					ForTotalCpuTime[0] = TotalCpuTime[j];
					ForIO[0] = IO[j];
					ArrivalTime[j] = ArrivalTime[i];
					B[j] = B[i];
					TotalCpuTime[j] = TotalCpuTime[i];
					IO[j] = IO[i];
					
					ArrivalTime[i] = ForArrivalTime[0];
					B[i] = ForB[0];
					TotalCpuTime[i] = ForTotalCpuTime[0];
					IO[i] = ForIO[0];
				 }
				 else if(ArrivalTime[i] == ArrivalTime[j]) {
					 System.out.println("4");
					 if(B[i] < B[j]) {
						 ForArrivalTime[0] = ArrivalTime[j];
						 ForB[0] = ArrivalTime[j];
						ForTotalCpuTime[0] = TotalCpuTime[j];
						ForIO[0] = IO[j];
						ArrivalTime[j] = ArrivalTime[i];
						B[j] = B[i];
						TotalCpuTime[j] = TotalCpuTime[i];
						IO[j] = IO[i];
						
						ArrivalTime[i] = ForArrivalTime[0];
						B[i] = ForB[0];
						TotalCpuTime[i] = ForTotalCpuTime[0];
						IO[i] = ForIO[0];
					 }
					 if(B[i] == B[j]) {
						 if(TotalCpuTime[i] < TotalCpuTime[j]) {
							 System.out.println("5");
							ForArrivalTime[0] = ArrivalTime[j];
							ForB[0] = ArrivalTime[j];
							ForTotalCpuTime[0] = TotalCpuTime[j];
							ForIO[0] = IO[j];
							ArrivalTime[j] = ArrivalTime[i];
							B[j] = B[i];
							TotalCpuTime[j] = TotalCpuTime[i];
							IO[j] = IO[i];
							
							ArrivalTime[i] = ForArrivalTime[0];
							B[i] = ForB[0];
							TotalCpuTime[i] = ForTotalCpuTime[0];
							IO[i] = ForIO[0]; 
						 }
						 else if(TotalCpuTime[i] == TotalCpuTime[j]) {
							 System.out.println("6");
							 if(IO[i]< IO[j]) {
									ForArrivalTime[0] = ArrivalTime[j];
									ForB[0] = ArrivalTime[j];
									ForTotalCpuTime[0] = TotalCpuTime[j];
									ForIO[0] = IO[j];
									ArrivalTime[j] = ArrivalTime[i];
									B[j] = B[i];
									TotalCpuTime[j] = TotalCpuTime[i];
									IO[j] = IO[i];
									
									ArrivalTime[i] = ForArrivalTime[0];
									B[i] = ForB[0];
									TotalCpuTime[i] = ForTotalCpuTime[0];
									IO[i] = ForIO[0]; 
							 }
						 }
					 }
				 }
			 }
		 } // end of sort
		 
		 for( int i=0; i<numOfProcess; i++) {
			 System.out.println("Sorted process " + i);
		 System.out.println(ArrivalTime[i]);
		 System.out.println(B[i]);
		 System.out.println(TotalCpuTime[i]);
		 System.out.println(IO[i]);
		 System.out.println("///////////////////////////");
	}
		
		 
		 
		 
	}
	
	public static void main(String[] args) throws IOException {
		String filename;
		String randomNumFile;
		filename = args[0];
		if(args[1].contains("--verbose")) {
			randomNumFile = args[2];
		}
		else {
			randomNumFile = args[1];
		}
		Scanner input = newScanner(filename);
		Scanner otherInput = newScanner(randomNumFile);	
		sorting(input, otherInput);
	}


	



}

