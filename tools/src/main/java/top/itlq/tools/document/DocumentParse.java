package top.itlq.tools.document;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentParse {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(DocumentParse.class.getResourceAsStream("doc")));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("doc_new.txt")));
        StringBuilder essay = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null){
            essay.append(line);
        }
        Pattern pattern = Pattern.compile("<span .*? data-src-str=\"(.*?)\" .*?>(.*?)</span>");
        Matcher matcher = pattern.matcher(essay);
        while (matcher.find()){
            bw.write(matcher.group(1));
            bw.newLine();
            bw.write(matcher.group(2));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
}
