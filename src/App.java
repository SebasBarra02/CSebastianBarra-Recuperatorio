import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    private static BufferedImage applyFilter(BufferedImage image, int option) {
        Filters filters = new Filters();
        BufferedImage filteredImage = null;

        if(option == 1){
            filteredImage = filters.applyBlackAndWhiteFilter(image);
        } else if (option == 2) {
            filteredImage = filters.applySepiaFilter(image);
        } else {
            filteredImage = filters.applyColorsInvertedFilter(image);
        }
        return filteredImage;
    }

    public static BufferedImage mergeSubpictures(BufferedImage[] subImages, int numCores) {
        int subImageWidth = subImages[0].getWidth();
        int subImageHeight = subImages[0].getHeight();
        int mergedWidth = subImageWidth * numCores;
        int mergedHeight = subImageHeight * numCores;
        BufferedImage mergedImage = new BufferedImage(mergedWidth, mergedHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < numCores; i++) {
            for (int j = 0; j < numCores; j++) {

                int x = i * subImageWidth;
                int y = j * subImageHeight;
                int subImageIndex = i * numCores + j;
                BufferedImage subImage = subImages[subImageIndex];
                BufferedImage subImageCopy = subImage.getSubimage(0, 0, subImage.getWidth(), subImage.getHeight());
                mergedImage.getGraphics().drawImage(subImageCopy, x, y, null);

            }
        }
        return mergedImage;
    }

    public static void processImage(int option, int numCores, String imagePath){
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int subImageWidth = width / numCores;
            int subImageHeight = height / numCores;

            ExecutorService executorService = Executors.newFixedThreadPool(numCores);
            BufferedImage[] processedSubImages = new BufferedImage[numCores * numCores];

            for (int i = 0; i < numCores; i++) {
                for (int j = 0; j < numCores; j++) {
                    int x = i * subImageWidth;
                    int y = j * subImageHeight;
                    BufferedImage subImage = originalImage.getSubimage(x, y, subImageWidth, subImageHeight);

                    int subImageIndex = i * numCores + j;

                    executorService.submit(() -> {
                        processedSubImages[subImageIndex] = applyFilter(subImage, option);
                    });
                }
            }

            executorService.shutdown();

            while (!executorService.isTerminated()) {

            }
            BufferedImage consolidatedImage = mergeSubpictures(processedSubImages, numCores);
            saveImage(consolidatedImage, imagePath, option);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImage(BufferedImage consolidatedImage, String path, int optionFilter) throws IOException {
        String originalPath = path;
        String modifiedPath;
        File outputImageFile = null;

        switch (optionFilter){
            case 1:
                modifiedPath = originalPath.replace(".jpg", "-blackAndWhite.jpg");
                outputImageFile = new File(modifiedPath);
                ImageIO.write(consolidatedImage, "jpg", outputImageFile);
                System.out.println("The image was saved in: "+ modifiedPath);
                break;
            case 2:
                modifiedPath = originalPath.replace(".jpg", "-sepia.jpg");
                outputImageFile = new File(modifiedPath);
                ImageIO.write(consolidatedImage, "jpg", outputImageFile);
                System.out.println("The image was saved in: "+ modifiedPath);
                break;
            default:
                modifiedPath = originalPath.replace(".jpg", "-colorInverted.jpg");
                outputImageFile = new File(modifiedPath);
                ImageIO.write(consolidatedImage, "jpg", outputImageFile);
                System.out.println("The image was saved in: "+ modifiedPath);
                break;
        }
    }
}
