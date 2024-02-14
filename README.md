Purpose of this program is to represent "grep" command functionality in Linux. The program will seach the give input 
string in all the files present in directories and nested directories in the s3 bucket that is in between the provided 
start and end date inclusively. 

The directories are stored in the s3 bucket in the below format.
    
    yyyy-MM-dd

The directories further contains the files in the format 

    01.txt
    02.txt

REST ENDPOINT: The program expose the following rest end point

    /LogPuller/fetchLogs

INPUT: Input to the program are expected in the below format, the program will search the provided search string in all 
the files in the above-mentioned directories.

    {
        "searchString" : "hello",
        "startDate" : "2024-02-10",
        "endDate" : "2024-02-12"
    }

OUTPUT: The program will output list of files that contains the provided search string. The output of the program is in
the below format, where the logData is the line which contains the given input string, along with the file name and line
number: 

        {
            "fileName": "2024-02-10/01.txt",
            "logData": "helloworldhellohellohellohellohellohellohellohello",
            "lineNumber": 1
        }

HOW TO RUN THE PROGRAM: (IntelliJ IDE)

* Clone the project from Git repo. 
* Go to File -> New Project -> Project from version control
* A window will appear
* select the pom.xml file, the project will open
* Go to Run/Edit configurations 
* click the + icon, select application 
* select the main class as LogPullerApplication, click ok
* The program can not be run from tne run button

The program can also run in a Docker container: 

* run command "mvn package" 
* run command "docker build -t logpuller.jar ."
* A docker image with name logpuller.jar will be created
* run the image with command "docker run -p 9090:8080 logpuller.jar"

HOW TO TEST THE PROGRAM

The program can be tested by follwing ways: 

* hit a curl get request with json payload
* hit the get end point with postman(preferred)

AWS_S3_CONFIGURATION

    The bucket file name, access key and secret key are configurable in the s3.properties file present in the resources
    by default it has read access to the bucket that develper @Shubham has created. 

A lot of further enhancements can be done in this project based on the requirements some of them are: 

* configure environment wise properties from helm charts in Kubernetes
* case-insensitive search functionality
* other grep command arguments 

