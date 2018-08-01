# Scheduler
implement scheduling in order to see how the time required depends on the scheduling algorithm and the request patterns.    
A process is characterized by just four non-negative integers A, B, C, and IO. A is the arrival time of the process and C is the total CPU time needed. The I/O times that alternates with computation times are uniformly distributed random integers in the interval (0, B].    
The following kinds of scheduling algorithms are uploaded
• FCFS  
• RR with quantum 2.  
• Uniprogrammed. Just one process active. When it is blocked, the system waits.  
• SRTN (PSJF). This is preemptive. Recall that the remaining time is for the process, not the burst.  
So the time you use to determine priority is the total time remaining (i.e., the input value C minus  
the number of cycles this process has run).  

### Compiling
```
javac Scheduling.java
```

### Running
Provide the text-based input as the first and only command line argument in the following way. See sample [input files](/inputs) as well as their [corresponding outputs](/outputs). Add an optional verbose command to produce a more detailed output.
```
java Scheduling input(1~7).txt + optional -verbose
