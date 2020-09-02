import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    private int[] ArrivalTime;
    private int[] B;
    private int[] IO;
    private int[] TotalCpuTime;
    private int[] ForArrivalTime;
    private int[] ForB;
    private int[] ForIO;
    private int[] ForTotalCpuTime;
    private ArrayList allData;
    private int numOfProcess;
    
    
    public Database(ArrayList <String> allData) {
    	this.allData = allData;
    	numOfProcess = Integer.parseInt((String) allData.get(0));
    	ArrivalTime = new int[numOfProcess];
    	B = new int[numOfProcess];
    	IO = new int[numOfProcess];
    	TotalCpuTime = new int[numOfProcess];
    	ForArrivalTime = new int[numOfProcess];
    	ForB = new int[numOfProcess];
    	ForIO = new int[numOfProcess];
    	ForTotalCpuTime = new int[numOfProcess];
    	DataInitiate();
    }
    
    public void DataInitiate() {
        int counter =0;
        for (int i = 0; i < (numOfProcess * 4); i += 4) {
            ArrivalTime[counter] = Integer.parseInt((String) allData.get(i + 1));
            B[counter] = Integer.parseInt((String) allData.get(i + 2));
            TotalCpuTime[counter] = Integer.parseInt((String) allData.get(i + 3));
            IO[counter] = Integer.parseInt((String) allData.get(i + 4));
            ForArrivalTime[counter] = Integer.parseInt((String) allData.get(i + 1));
            ForB[counter] = Integer.parseInt((String) allData.get(i + 2));
            ForTotalCpuTime[counter] = Integer.parseInt((String) allData.get(i + 3));
            ForIO[counter] = Integer.parseInt((String) allData.get(i + 4));
            counter++;
        }
    }

    //Getters and Setters
	public int[] getArrivalTime() {
		return ArrivalTime;
	}

	public void setArrivalTime(int[] arrivalTime) {
		ArrivalTime = arrivalTime;
	}

	public int[] getB() {
		return B;
	}

	public void setB(int[] b) {
		B = b;
	}

	public int[] getIO() {
		return IO;
	}

	public void setIO(int[] iO) {
		IO = iO;
	}

	public int[] getTotalCpuTime() {
		return TotalCpuTime;
	}

	public void setTotalCpuTime(int[] totalCpuTime) {
		TotalCpuTime = totalCpuTime;
	}

	public int[] getForArrivalTime() {
		return ForArrivalTime;
	}

	public void setForArrivalTime(int[] forArrivalTime) {
		ForArrivalTime = forArrivalTime;
	}

	public int[] getForB() {
		return ForB;
	}

	public void setForB(int[] forB) {
		ForB = forB;
	}

	public int[] getForIO() {
		return ForIO;
	}

	public void setForIO(int[] forIO) {
		ForIO = forIO;
	}

	public int[] getForTotalCpuTime() {
		return ForTotalCpuTime;
	}

	public void setForTotalCpuTime(int[] forTotalCpuTime) {
		ForTotalCpuTime = forTotalCpuTime;
	}

	public ArrayList getAllData() {
		return allData;
	}

	public void setAllData(ArrayList allData) {
		this.allData = allData;
	}

	public int getNumOfProcess() {
		return numOfProcess;
	}

	public void setNumOfProcess(int numOfProcess) {
		this.numOfProcess = numOfProcess;
	}
    
    
}
