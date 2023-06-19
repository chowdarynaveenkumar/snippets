1. Download the file. Say it is downloaded to C:\temp
2. Ensure that the JDK binaries are in the PATH.
3. Compile the file
   
   javac SybaseDatabaseConnection
5. Run

   Usage: java -cp "Full path to jconn jar";. SybaseDatabaseConnection "Sybase Server" "Sybase Port" "Database name" "User Name" "Password" "Query"

Note:
1. This sample program is built with Java version 1.8.0_202. Hence, the sample would run only with Java runtime 1.8.0_202 or above
2. This sample program prints only the first column of the query result. Also, make sure that the column results are of type character/string.
