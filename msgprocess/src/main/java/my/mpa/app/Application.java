package my.mpa.app;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import my.mpa.core.SalesEngine;
import my.mpa.sales.msg.Message;

public class Application {

	
	public static void main(String[] args) {
		//Storage data and message files by default from jar resource file
        String storageDataFile = "products.csv";
        String messagesDataFile = "mixedmessages.json";
        boolean isFromResource = true;
		
        if(args.length == 2) {
        	//takes files from command line
        	storageDataFile = args[0];
        	messagesDataFile = args[1];
        	if (isInvalidFilePath(storageDataFile)) {
                System.out.println("Incorrect storage data file. ");
                System.exit(1);
        	}
           	if (isInvalidFilePath(messagesDataFile)) {
                System.out.println("Incorrect messsages file. ");
                System.exit(1);
        	}
           	isFromResource = false;
        } else if (args.length > 0){
            System.out.println("Incorrect number of commandline arguments.");
            System.exit(1);
        }

        
        SalesEngine salesEngine = SalesEngine.getSalesEngine();

        //get
        boolean initialized = salesEngine.initialize(getFile(storageDataFile, isFromResource));
        if(!initialized) {
            System.out.println("Stock initialization failed. Check console. Inform developer.");
            System.exit(1);
        }

        List<Message> messages = salesEngine.parse(getFile(messagesDataFile, isFromResource));
        if(messages == null) {
            System.out.println("Messages data files cannot be parsed.");
            System.exit(1);
        }

        boolean processed = salesEngine.process(messages);
        if(!processed) {
            System.out.println("Sale messages processing failed.");
            System.exit(1);
        }

		
	}

	private static File getFile(String filePath, boolean isFromResource) {
		File dataFile = null;
		if (isFromResource) {
			//load file from resource
			URL uri = SalesEngine.class.getClassLoader().getResource(filePath);
			dataFile = new File(uri.getFile());
		} else {
			dataFile = new File(filePath);
		}
		return dataFile;
	}

	/**
	 * Check input file from command line arguments
	 * @param filePath
	 * @return
	 */
    private static boolean isInvalidFilePath(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if(!Files.exists(path) || Files.notExists(path)) {
                return true;
            }

            if(!Files.isRegularFile(path)) {
                return true;
            }

            if(!Files.isReadable(path)) {
                return true;
            }
        } catch (InvalidPathException | NullPointerException exception) {
            return true;
        }

        return false;
    }
	
}
