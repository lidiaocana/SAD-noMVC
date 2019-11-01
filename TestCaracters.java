import java.util.Scanner;
import java.io.*;

class TestCaracters {
    public static void main(String[] args) {
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        try {
            //in.setRaw();
            int str = 0;
            str = in.read();
            System.out.println("\nline is: " + str +"\n");
            //in.unsetRaw();
    
            in.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}