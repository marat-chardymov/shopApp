package com.epam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Marat_Chardymau on 3/24/14.
 */
public class AAA {
    public static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter(new File("RESULT.TXT"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
    int n = 30;
        for(int i =0; i<n;i++){
            for (int j =0; j<(n-i);j++) {
                writer.print(" ");
            }
            count(i);
            writer.println("");
        }
        writer.close();
    }
    static private int g(int n, int g) {
        if (g <= 1 || g >= (n - 1)) {
            return 1;
        }
        int result = 1;
        int c = 2;
        while (c <= g) {
            int d = n - g;
            if (c <= d) {
                result += g(d, c);
            }
            c++;
        }
        return result;
    }

    static public int count(int v) throws FileNotFoundException {

        int result = (int) (1 - Math.signum(Math.abs(v)));
        int c = 1;
        while (c <= v) {
            int gopa=g(v, c);
            result += gopa;
            writer.print(gopa);
            writer.print(" ");
            c++;
        }
        return result;
    }

}
