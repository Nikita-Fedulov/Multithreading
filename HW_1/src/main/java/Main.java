import java.io.File;

public class Main {
    private  static int newWidth = 256;
    public static void main(String[] args)
    {

        Runtime runtime = Runtime.getRuntime();

        int nrOfProcessors = runtime.availableProcessors();

        System.out.println("Number of processors available to the Java Virtual Machine: " + nrOfProcessors);


        String srcFolder = "C:\\Users\\nikim\\OneDrive\\Рабочий стол\\2018__08";
        String dstFolder = "C:\\Users\\nikim\\OneDrive\\Рабочий стол\\N";

        File srcDir = new File(srcFolder);


        File[] files = srcDir.listFiles();

//        startThread(nrOfProcessors, files, dstFolder);


        long start = System.currentTimeMillis();

        int middle = files.length / nrOfProcessors;


        File[] files1 = new File[middle];
        System.arraycopy(files, 0, files1, 0, middle);
        ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
        new Thread(resizer1).start();


        File[] files2 = new File[files.length - middle * 2];
        System.arraycopy(files, middle * 2, files2, 0, files2.length);
        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();

        File[] files3 = new File[files.length - middle * 3];
        System.arraycopy(files, middle * 3, files3, 0, files3.length);
        ImageResizer resizer3 = new ImageResizer(files3, newWidth, dstFolder, start);
        new Thread(resizer3).start();


        File[] files4 = new File[files.length - middle * 4];
        System.arraycopy(files, middle * 4, files4, 0, files4.length);
        ImageResizer resizer4 = new ImageResizer(files4, newWidth, dstFolder, start);
        new Thread(resizer4).start();

        File[] files5 = new File[files.length - middle * 5];
        System.arraycopy(files, middle * 5, files5, 0, files5.length);
        ImageResizer resizer5 = new ImageResizer(files5, newWidth, dstFolder, start);
        new Thread(resizer5).start();


    }


    private static void startThread (int nrOfProcessors, File[] files, String dstFolder){

        long start = System.currentTimeMillis();

        int middle = files.length / nrOfProcessors;

        for (int i = 0; i < nrOfProcessors - 1; i++){
            File[] files1 = new File[middle];
            System.arraycopy(files, 0 + (middle * i), files1, 0, middle);
            ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
            new Thread(resizer1).start();
        }

        File[] files2 = new File[files.length - middle * (nrOfProcessors - 1)];

        System.arraycopy(files, middle * (nrOfProcessors - 1), files2, 0, files2.length);
        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();
    }
}
