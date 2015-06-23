package pl.edu.agh.gitclient.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {


    public static List<String> LoadFile(String path){
        return LoadFile(new File(path));
    }

    public static List<String> LoadFile(File file){
        List<String> fileContent = new ArrayList<String>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        }

        catch (Exception e){
            return  null;
        }
/*

        while (scanner.hasNext()){
            fileContent.add(scanner.next());
        }
        scanner.close();

*/


        return  fileContent;
    }
}
