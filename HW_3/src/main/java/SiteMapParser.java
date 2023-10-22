import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SiteMapParser extends RecursiveTask<String> {


    private String url;
    private static String startUrl;
    private static CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();

    public SiteMapParser(String url) {
        this.url = url;
    }

    public SiteMapParser(String url, String startUrl) {
        this.url = url;
        this.startUrl = startUrl;
    }

    @Override
    protected String compute() {

        StringBuffer sb = new StringBuffer(url + "\n" + "\t");
        Set<SiteMapParser> tasks = new HashSet<>();
        try {
            Thread.sleep(200);
            Document document = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .get();
            Elements elements = document.select("a");
            for (Element element : elements) {
                String teg = element.attr("href");
                if (!teg.isEmpty() && teg.startsWith(url)&& !allLinks.contains(teg) && !teg.contains("#"))
                {
                    allLinks.add(teg);
                    SiteMapParser link = new SiteMapParser(teg);
                    link.fork();
                    tasks.add(link);
                }
            }
            for (SiteMapParser teg : tasks) {
                sb.append(teg.join());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
