public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        String path = menu.pathImage();
        int cores = menu.cores();
        int filterOption = menu.menuOptionFilter();
        App.processImage(filterOption, cores, path);
    }
}
