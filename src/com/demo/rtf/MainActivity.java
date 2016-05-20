package com.demo.rtf;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            for(int k=2; k<45; k++){
                br = new BufferedReader(new FileReader("/Users/varinderatwal/Desktop/Project/Sanitized"+k+".rtf"));
                while ((sCurrentLine = br.readLine()) != null) {
                    String[] str = sCurrentLine.split("par");
                    for(int i=0;i<str.length;i++){
                        Node node = new Node();
                        String time = getDate(str[i]);
                        node.time = time;
                        if(time !=null){
                            String[] msg = str[i].split(time);
                            if(msg.length == 2){
                                String userText = msg[1];
                                if(userText.split(" - ").length == 2){
                                    String[] user = userText.split(" - ");
                                    node.user = user[1].split(":")[0];
                                    node.text = msg[1];
                                    parseOrder(node);
                                }
                            }
                        }
                    }
                }
                OrderHandler orderHandler = OrderHandler.getInstance();
                orderHandler.showAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private static void parseOrder(Node node) {
        String text = node.text;
        text = text.toLowerCase();
        Pattern pattern = Pattern.compile("(\\w+[\\s+]?)(kg|gm\\b)(\\s+\\w+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            Order order = new Order();
            order.user = node.user;
            order.placeTime = node.time;
            order.qty = matcher.group(1) + matcher.group(2);
            order.item = matcher.group(3);
            OrderHandler orderHandler = OrderHandler.getInstance();
            orderHandler.addOrder(order);
        }
    }

    private static String getDate(String desc) {
        int count=0;
        String[] allMatches = new String[2];
        Matcher m = Pattern.compile("(0?[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d(?:, )(0?[1-9]|1[1-9]|2[123])[:](0?[1-9]|[1-5][1-9])(?: )[APap][Mm]").matcher(desc);
      	while (m.find()) {
            allMatches[count] = m.group();
            count++;
        }
        return allMatches[0];
    }
}
