Akka actor benchmark
=====================

Scala (Akka) actor performance test aka benchmark.

Program should be run with three arguments:
1.	actor count (A)  
2.	message count (M)  
3.	message processing time in milliseconds (T)  

If actor count == 1, then the script sends all M messages to that actor. If count > 1, then the script randomly sends M messages to A actors. Each message is processed by corresponding actor for T milliseconds. 

Use SBT for downloading Akka and running program.