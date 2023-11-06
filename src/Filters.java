import java.awt.image.BufferedImage;


public class Filters {
    public  BufferedImage applyBlackAndWhiteFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                int gray = (red + green + blue) / 3;

                int newPixel = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                image.setRGB(x, y, newPixel);
            }
        }

        return image;
    }

    public BufferedImage applySepiaFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                int newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
                int newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
                int newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

                newRed = Math.min(255, newRed);
                newGreen = Math.min(255, newGreen);
                newBlue = Math.min(255, newBlue);

                int newPixel = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;

                sepiaImage.setRGB(x, y, newPixel);
            }
        }

        return sepiaImage;
    }

    public BufferedImage applyColorsInvertedFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                int red = 255 - (pixel >> 16) & 0xFF;
                int green = 255 - (pixel >> 8) & 0xFF;
                int blue = 255 - pixel & 0xFF;

                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;

                invertedImage.setRGB(x, y, newPixel);
            }
        }

        return invertedImage;
    }


}