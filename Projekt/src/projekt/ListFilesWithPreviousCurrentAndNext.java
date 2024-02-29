
import java.io.File;
import java.io.IOException;
import projekt.CreateIndexHtml;
import projekt.HTML;
import projekt.DeleteHtmlFilesRecursively;


public class ListFilesWithPreviousCurrentAndNext {
    private static String previousFileName = "";
    private static String currentFileName = "";
    private static String nextFileName = "";

    public static File directory;
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("nincs args");
            return;
        }
////iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        String directoryPath = args[0];
//iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        
        directory = new File(directoryPath);
        new DeleteHtmlFilesRecursively(directory);
        
        if (directory.isDirectory()) {
            listFilesWithPreviousCurrentAndNext(directoryPath,directory);
        } else {
            System.out.println("A megadott elérési út nem egy mappa.");
        }
        new CreateIndexHtml(directoryPath);
    }

    private static void listFilesWithPreviousCurrentAndNext(String path ,File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                
                    if (!file.isDirectory()){
                        
                    currentFileName = file.getAbsolutePath();    
                    String previousFileName;
                    if (i > 0) {
                        if(files[i-1].getAbsoluteFile().isDirectory()) {
                            previousFileName = files[i - 2].getName();
                        }
                        else{
                        previousFileName = files[i - 1].getName();
                        }
                    } else {
                        previousFileName = "";
                    }
                    
                    currentFileName = file.getAbsolutePath();
                    String nextFileName;
                    if (i == files.length - 1) {
                        nextFileName = currentFileName;
                    } else {
                        if(files[i+1].getAbsoluteFile().isDirectory()) {
                        nextFileName = files[i + 2].getName();
                        } else {
                            nextFileName = files[i + 1].getName();
                        }
                    }

                    
                    new HTML(currentFileName,previousFileName,nextFileName,directory);

                    }
                
                if (file.isDirectory()) {
                    String fileName = file.getPath();
                    listFilesWithPreviousCurrentAndNext(fileName,file);
                }
            }
        }
    }


}
