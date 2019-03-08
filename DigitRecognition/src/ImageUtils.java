import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageUtils {
    public static BufferedImage rescale(BufferedImage image,int W,int H) {
        //rescale image to be of size W*H
        Image scaledImage = image.getScaledInstance(W,H, Image.SCALE_SMOOTH);
        image = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.drawImage(scaledImage,0,0,null);
        g.dispose();
        return image;
    }
    public static int [] [] getBitmap(BufferedImage image) {
        //returns bitmap of image
        int Height = image.getHeight(),Width = image.getWidth();
        int [] [] G = new int[Height][Width];
        for (int i = 0;i < Height;i++)
            for (int j = 0;j < Width;j++) {
                //get rgb of cell
                Color rgb = new Color(image.getRGB(i,j));
                //extract each color
                int r = rgb.getRed();
                int g = rgb.getGreen();
                int b = rgb.getBlue();
                int gray = (int)Math.floor(r*0.299+g*0.587+b*0.114);
                //if there is any color set cell to 1
                if (gray > 0) G[i][j] = 1;
            }
        return G;
    }
    public static BufferedImage trim(BufferedImage image) {
        //remove surrounding empty space
        //make non empty cells at center
        int [] [] bitmap = getBitmap(image);
        int H = image.getHeight(),W = image.getWidth();
        //get coordinates of smallest rectangle that encloses non empty cells
        int minI = H-1,minJ = W-1,maxI = 0,maxJ = 0;
        for (int i = 0;i < H;i++)
            for (int j = 0;j < W;j++){
                if (bitmap[i][j] == 0) continue;
                minI = Math.min(minI,i);
                maxI = Math.max(maxI,i);
                minJ = Math.min(minJ,j);
                maxJ = Math.max(maxJ,j);
            }
        // create new image that contains only non empty cells
        int newH = maxI-minI+1,newW = maxJ-minJ+1;
        int [] newBitmap = new int[newH*newW];
        for (int i = 0,k = 0;i < newH;i++)
            for (int j = 0;j < newW;j++)
                newBitmap[k++] = bitmap[i+minI][j+minJ];

        BufferedImage im = new BufferedImage(newW,newH,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = im.getRaster();
        raster.setPixels(0,0,newW,newH,newBitmap);
        return im;
    }
}
