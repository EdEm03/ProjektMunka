
package projekt;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CreateIndexHtml {

    
    
    public CreateIndexHtml(String root) throws IOException {
        
        createIndexHtml(root);
    }

    

    private static void createIndexHtml(String rootDirectory) throws IOException {
        Files.walkFileTree(Paths.get(rootDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                createIndexHtmlForDirectory(dir.toString(), rootDirectory);
                return FileVisitResult.CONTINUE;
            }
        });

        
        createIndexHtmlForDirectory(rootDirectory, rootDirectory);
    }

    private static void createIndexHtmlForDirectory(String directory, String rootDirectory) {
        String indexContent = generateIndexContent(directory, rootDirectory);
        Path indexPath = Paths.get(directory, "index.html");
        try {
            Files.write(indexPath, indexContent.getBytes());
        } catch (IOException e) {
            System.err.println("Error creating index.html for directory " + directory + ": " + e.getMessage());
        }
    }

    private static String generateIndexContent(String currentDirectory, String rootDirectory) {
        StringBuilder content = new StringBuilder("<html>\n<head>\n<title>Index Page</title>\n</head>\n<body>\n");

        
        Path rootIndex = Paths.get(rootDirectory, "index.html");
        String rootRelativePath = Paths.get(currentDirectory).relativize(rootIndex).toString().replace("\\", "/");
        content.append("<a href=\"").append(rootRelativePath).append("\"><button>gyökér index</button></a><br><br>");

        
        Path parentIndex = Paths.get(currentDirectory).getParent().resolve("index.html");
        String parentRelativePath = Paths.get(rootDirectory).relativize(parentIndex).toString().replace("\\", "/");
        content.append("<a href=\"../index.html\"><button>szülő index</button></a><br><br>");

        
        try {
            Files.walkFileTree(Paths.get(currentDirectory), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(Paths.get(currentDirectory))) {
                        String relativePath = Paths.get(currentDirectory).relativize(dir).toString().replace("\\", "/");
                        content.append("<a href=\"").append(relativePath).append("/index.html\"><button>").append(dir.getFileName()).append(" Index</button></a><br>\n");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        try {
            Files.walkFileTree(Paths.get(currentDirectory), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".html")) {
                        String relativePath = Paths.get(currentDirectory).relativize(file).toString().replace("\\", "/");
                        content.append("<a href=\"").append(relativePath).append("\">").append(file.getFileName()).append("</a><br>\n");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        content.append("</body>\n</html>");
        return content.toString();
    }
}
