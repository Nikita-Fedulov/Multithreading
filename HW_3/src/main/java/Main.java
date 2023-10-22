import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static String url = "https://skillbox.ru/";

    public static void main(String[] args) {
        System.out.println("Сканируем сайт...");
        SiteMapParser siteMapParser = new SiteMapParser(url, url);
        String siteMap = new ForkJoinPool().invoke(siteMapParser);
        System.out.println("Сканирование завершено...");
        writeFiles(siteMap);
    }

        private static void writeFiles(String siteMap){
            System.out.println("Пишем файл...");
            String filePath = "C:\\Users\\nikim\\OneDrive\\Рабочий стол\\Map.txt";
            File file = new File(filePath);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(siteMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Карта создана!");
        }
}