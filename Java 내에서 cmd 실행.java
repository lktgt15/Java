import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        try{
            Process process = Runtime.getRuntime().exec("cmd");

            OutputStream stdin = process.getOutputStream();
            InputStream stderr = process.getErrorStream();
            InputStream stdout = process.getInputStream();

            Executors.newCachedThreadPool().execute(()->{
               try(BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,"euc-kr"))){
                   String line;
                   while((line = reader.readLine()) != null){
                       System.out.println(line);
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
            });

            Executors.newCachedThreadPool().execute(()->{
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(stderr,"euc-kr"))){
                    String line;
                    while((line = reader.readLine()) != null){
                        System.out.println("err "+line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Executors.newCachedThreadPool().execute(()->{
                try(Scanner scan = new Scanner(System.in)){
                    try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin, "euc-kr"))){
                        while(true){
                            String input = scan.nextLine();
                            input += "\n";
                            writer.write(input);
                            writer.flush();
                            if("exit\n".equals(input)){
                                System.exit(0);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

// 참조 https://nowonbun.tistory.com/460
