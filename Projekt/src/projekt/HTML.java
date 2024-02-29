


package projekt;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.nio.file.Files;

public class HTML {

    private String filenameIn;
    private String last;
    private String next;
    private File root;

    public HTML(String filenameIn, String last, String next,File root) {
        this.filenameIn = filenameIn;
        this.last = last;
        this.next = next;
        this.root = root;
        generateHTMLFile();
    }

    private void generateHTMLFile() {
        try (Scanner fileIn = new Scanner(new FileReader(filenameIn));
             PrintWriter fileOut = new PrintWriter(getOutputFilePath())) {

            if (!fileIn.hasNextLine()) {
                System.out.println("This file is empty");
            } else {
                String png = Paths.get(filenameIn).getFileName().toString();
                last = formatToHtmlFileName(last);
                next = formatToHtmlFileName(next);

                
                String rootIndex = findRootIndex(filenameIn);

                fileOut.println("<html>");
                fileOut.println("<head>");
                fileOut.println("</head>");
                fileOut.println("<body>");
                fileOut.println("<div>");
                fileOut.println("<a href=\"" + rootIndex + "\">"); // Use rootIndex for the link
                fileOut.println("<button> Start Page </button>");
                fileOut.println("</a>");
                fileOut.println("</div>");
                fileOut.println("<div>");
                fileOut.println("-----------------------------------------------------");
                fileOut.println("</div>");
                fileOut.println("<div>");
                fileOut.println("<a href=\"" + "index.html" + "\">");
                fileOut.println("<button> ^^ </button>");
                fileOut.println("</a>");
                fileOut.println("</div>");
                fileOut.println("<div>");
                fileOut.println("<a href=\"" + last + "\">");
                fileOut.println("<button> << </button>");
                fileOut.println("</a>");
                fileOut.println(png);
                fileOut.println("<a href=\"" + next + "\">");
                fileOut.println("<button> >> </button>");
                fileOut.println("</div>");
                fileOut.println("<div>");
                fileOut.println("<img src=\"" + png + "\" height=\"300\">");
                fileOut.println("</div>");
                fileOut.println("</body>");
                fileOut.println("</html>");

                System.out.println("done :)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
            e.printStackTrace();
        }
    }

    private String getOutputFilePath() {
        Path outputPath = Paths.get(filenameIn);
        String fileName = outputPath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        String baseName = (dotIndex != -1) ? fileName.substring(0, dotIndex) : fileName;
        return outputPath.resolveSibling(baseName + ".html").toString();
    }

    private String formatToHtmlFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex != -1) ? fileName.substring(0, dotIndex) + ".html" : fileName;
    }

    private String findRootIndex(String currentFilePath) {

        File file = new File(currentFilePath);
        StringBuilder stringBuilder = new StringBuilder();
        int a=countIndexHtmlFiles(file,root);
        for(int i =0; i>a;i++){
            stringBuilder.append("../");
        }
        stringBuilder.append("index.html");
        return stringBuilder.toString();
    }
    
    
    private static int countIndexHtmlFiles(File file, File baseDirectory) {
        int count = 0;
        String asd = file.getAbsolutePath();

        // Check if the input file is not null and is a file
        if (file != null && file.isFile()) {
            // Get the parent directory of the input file
            File directory = file.getParentFile();

            
            int depth = 0;
            while (directory != null && !directory.equals(baseDirectory)) {
                // Check if the directory contains an "index.html" file
                File indexHtmlFile = new File(directory, "index.html");
                if (indexHtmlFile.exists() && indexHtmlFile.isFile()) {
                    count++;
                }

                
                directory = directory.getParentFile();
                depth++;
            }

            
            if (directory != null && directory.equals(baseDirectory)) {
                
                System.out.println("Depth of file relative to base directory: " + depth);
            }
        }

        return count;
    }


}
