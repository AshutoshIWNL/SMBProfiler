# SMBProfiler

SMBProfiler is a Java application designed for profiling SMB (Server Message Block) operations. It reads configuration properties, performs SMB operations iteratively, generates reports, and prints profiling results.

## Features
- **Configuration Management**: Read configuration properties from a file to specify SMB server details, authentication credentials, and operation parameters.
- **Profiling**: Perform SMB operations iteratively based on specified configuration parameters.
- **Report Generation**: Generate reports summarizing the profiling results.

## Usage
To use SMBProfiler, follow these steps:

1. **Clone the Repository**: Clone the SMBProfiler repository to your local machine.

2. **Build**: Build the tool using the below command in the root of the project

   ```bash
   mvn clean compile assembly:single
   ```

3. **Run**: Run the SMBProfiler class with the path to the configuration file as a command-line argument.

   ```bash
   java -jar SMBProfiler-1.2-SNAPSHOT.jar /path/to/config.properties
   ```

   Replace `/path/to/config.properties` with the path to your configuration file.

4. **View Results**: Once profiling is complete, the application will generate a report file containing profiling statistics. You can view this file to analyze the results.

## Configuration
SMBProfiler expects a configuration file in Java Properties format. The following properties are supported:

- `smb.server_address`: The address of the SMB server.
- `smb.username`: The username for authentication.
- `smb.password`: The password for authentication.
- `smb.domain`: The domain for authentication.
- `smb.share_name`: The name of the shared folder on the SMB server.
- `smb.file_name`: The name of the file to be used in SMB operations.
- `smb.dir_name`: The name of the directory to be used in SMB operations.

Example configuration file (`config.properties`):

```properties
smb.server_address=192.168.1.100
smb.username=user
smb.password=password
smb.domain=WORKGROUP
smb.share_name=shared_folder
smb.file_name=test_file.txt
smb.dir_name=test_directory
```

