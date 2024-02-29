package projekt;


import java.io.File;

public class DeleteHtmlFilesRecursively {

    private File directory;
    
    public DeleteHtmlFilesRecursively(File directory) {
        deleteHtmlFilesRecursively(directory);
    }


    private void deleteHtmlFilesRecursively(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                
                if (file.getName().toLowerCase().endsWith(".html")) {
                    
                    file.delete();
                    System.out.println("Törölve: " + file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    
                    deleteHtmlFilesRecursively(file);
                }
            }
        }
    }
}
