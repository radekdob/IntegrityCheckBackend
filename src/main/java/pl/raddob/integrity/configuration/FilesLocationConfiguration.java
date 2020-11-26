package pl.raddob.integrity.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilesLocationConfiguration {

    @Value("${working.directory: Hello world}")
    private String workingDirectory;

    @Value("${file.name: file_to_check}")
    private String fileName;


    @Value("${connection.timeout}")
    private int connectionTimeout;

    @Value("${read.timeout}")
    private int readTimeout;


    public FilesLocationConfiguration() {
    }


    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }


    public String getFileName() {
        return fileName;
    }



    public boolean isWindows(){
        return System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
    }
}
