package pokergamecore;
import java.util.*;
import java.io.*;
public class OutFile 
{
    private List<String> lines;
    
    public OutFile()
    {
        lines = new ArrayList<>();
        lines.add("");
        lines.add("");
    }
    
    public void addLine()
    {
        lines.add("");
    }
    
    public void addLine(String outString)
    {
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + outString);
        lines.add("");
    }
    
    public void addTopLine()
    {
        lines.add(0, "");
    }
    
    public void addTopLine(String outString)
    {
        lines.set(0, lines.get(0) + outString);
        lines.add(0, "");
    }
    
    public void appendLine(String outString)
    {
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + outString);
    }
    
    public void appendTopLine(String outString)
    {
        lines.set(0, lines.get(0) + outString);
    }
    
    public void writeLinesToFile(String path)
    {
        File file = new File(path);
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException ex)
            {
                ex.printStackTrace(System.out);
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile())))
        {
            for(int i = 0; i < lines.size(); i++)
            {
                bw.write(lines.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException ex)
        {
            System.out.println("Error writing results to file");
            ex.printStackTrace(System.out);
        }
    }
}
