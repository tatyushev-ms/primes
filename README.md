# primes
Home task from CROSS

## Task description

#### Test assignment
Create a program written in Java that will run from a command line. The only input parameter is the data file described below.
The program should write all Prime Numbers found in the data file to the standard output (one line of output = one found Prime Number).
Any publicly available libraries can be used.
The output is an executable jar file including the source code or an Eclipse project.

#### Data file description
The data file is in Excel 2007-2013 (XLSX) format.
Data is located in the first sheet in the B column (data is entered as a text field).
Only positive Integers are valid data. Invalid data should be ignored by the program.
A small SAMPLE of data is attached. The final data may be different!

# Instructions

### Libraries

#### Access Microsoft Format Files 

   - Apache POI

#### Primality test algorithm
   
   - Apache Commons Math 
   
#### Unit test

   - JUnit 5
   - Hamcrest
   - Mockito
   
### Build

    mvn package

### Run

    java -jar ./target/primes-0.0.1.jar "./src/test/resources/normal.xlsx"

#### More information

 - The program respects [exit status](https://en.wikipedia.org/wiki/Exit_status) and [standard streams](https://en.wikipedia.org/wiki/Standard_streams).  
 - To use own implementation of the trial division as primality test algorithm set the *ALG* [environment variable](https://en.wikipedia.org/wiki/Environment_variable) to *TRIAL*. For, example:

    ```
    ALG=TRIAL java -jar ./target/primes-0.0.1.jar "./src/test/resources/normal.xlsx"
    ```
