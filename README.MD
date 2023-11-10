
A simple demo program created for the purpose of applying for developer position.

Program creates one background producer thread and one background consumer thread.
Threads communicate using FIFO queue and are decoupled.
I chose blocking queue implementation and program ends when consumer can not find any more commands in the queue in a given time (500ms)

I chose embedded derby database and JDBC API for access.
Although the program will work if I add more producer or consumer threads, access to database is serialized so there will be no performance benefit.
In real world application the persistence layer would be much more robust and the number of producers and consumers would be dynamic.

In the end I would like to thank you for this exercise, it was fun and I had to le-learn many things that I have not used in a long time :)

