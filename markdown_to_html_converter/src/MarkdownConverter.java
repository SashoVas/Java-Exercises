import converters.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MarkdownConverter implements MarkdownConverterAPI{
    private static final String HTML_FILE_START= """
        <html>
        <body>""";
    private static final String HTML_FILE_END="""
        </html>
        </body>""";

    @Override
    public void convertMarkdown(Reader source, Writer output) {
        var bufferedReader=new BufferedReader(source);
        var bufferedWriter=new BufferedWriter(output);
        List<Converter> converters=List.of(new HeadingConverter(),new BoldConverter(),new ItalicConverter(),new CodeFragmentConverter());
        String line;
        try {
            bufferedWriter.write(HTML_FILE_START+System.lineSeparator());

            while ((line = bufferedReader.readLine()) != null) {
                for(var converter:converters){
                    if (converter.isApplicable(line)){
                        line=converter.apply(line);
                    }
                }
                bufferedWriter.write(line+System.lineSeparator());
            }
            bufferedWriter.write(HTML_FILE_END);
        }
        catch(IOException e){
            throw new RuntimeException("Error occurred while processing file", e);

        }
    }

    @Override
    public void convertMarkdown(Path from, Path to) {
        try(var bufferedReader = new FileReader(from.toString())){
            var bufferedWriter=new FileWriter(to.toString());
            convertMarkdown(bufferedReader,bufferedWriter);
        }
        catch (IOException e){
            throw new RuntimeException("Error occurred while processing file", e);
        }
    }

    @Override
    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.md")) {
            for (Path file : stream) {
                String sourceFileName = file.getFileName().toString();
                String sourceFileNameWithoutExtension = sourceFileName.substring(0, sourceFileName.indexOf("."));
                Path targetFile = targetDir.resolve(sourceFileNameWithoutExtension + ".html");
                convertMarkdown(file, targetFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting markdown files", e);
        }
    }
}
