1. Download the files. Say it is downloaded to C:\temp
2. Ensure that the JDK binaries are in the PATH.
3. Compile the file(Optional)
   
   javac SybaseDatabaseConnectionKerberos.java
4. Run

   Usage: java -cp "Full path to jconn jar";. SybaseDatabaseConnectionKerberos "Sybase Server" "Sybase Port" "Database name" "Service Principal Name" "Query"

Note:
1. This sample program is built with Java version 1.8.0_202. Hence, the sample would run only with Java runtime 1.8.0_202 or above
2. This sample program prints only the first column of the query result. Also, make sure that the column results are of type character/string.
