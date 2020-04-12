package presentation;

import java.io.*;
import java.util.*;

/**
 * The parser class takes a text file and extracts the commands in a usable format for the pdf file creator.
 */
public class Parser {

    /**
     * This methods processes a text file taking each command and splitting it into the respective commands and arguments.
     * @param path a string representing the path to the text file to be processed
     * @return returns a linked hash map containing the operations as the keys and the arguments as values
     */
    public Map<String[], String[]> processFile(String path){
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String[], String[]> map = new LinkedHashMap<>();
        String[] substr;
        String[] op = new String[2];
        String[] param;
        for(String s : list){
            try{
                substr = s.split(":");
                op = substr[0].split(" ");
                param = substr[1].split(",");
                int i = 0;
                while(i < param.length){
                    param[i] = param[i].substring(1);
                    i++;
                }
                map.put(op, param);
            } catch (IndexOutOfBoundsException e){
                map.put(op, new String[]{"no parameters"});
            }
        }

        return map;
    }

}
