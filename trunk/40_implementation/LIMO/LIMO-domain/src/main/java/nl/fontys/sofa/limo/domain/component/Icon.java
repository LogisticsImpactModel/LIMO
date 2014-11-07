package nl.fontys.sofa.limo.domain.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

/**
 * An icon used by hubs and legs.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Icon implements Serializable {

    private byte[] data;
    private String imageType;
    private transient BufferedImage image;
    private static final transient int ICON_WIDTH = 64, ICON_HEIGHT = 64;

    /*
     CONSTRUCTORS
     */
    public Icon() {
        imageType = "";
        data = new byte[]{};
    }

    public Icon(byte[] data, String imageType) {
        this.data = data;
        this.imageType = imageType;
        this.buildImageFromData();
    }

    public Icon(Image image, String imageType) {
        this.setImage(image, imageType);
        this.resizeIcon();
    }

    /*
     GETTERS
     */
    public byte[] getData() {
        return this.data;
    }

    public BufferedImage getImage() {
        if (this.image == null) {
            this.buildImageFromData();
        }
        return this.image;
    }

    public String getImageType() {
        return this.imageType;
    }

    /*
     SETTERS
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setData(byte[] data, String imageType) {
        this.data = data;
        this.imageType = imageType;
        this.buildImageFromData();
    }

    /**
     * Set icon by image.
     *
     * @param image New icon image.
     * @param imageType Type of the image.
     */
    public final void setImage(Image image, String imageType) {
        this.imageType = imageType;
        if (!(image instanceof BufferedImage)) {
            this.image = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D bGr = this.image.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
        } else {
            this.image = (BufferedImage) image;
        }
        resizeIcon();
    }

    /**
     * Set icon by path. Old one will not change upon error.
     *
     * @param imagePath Path to new icon.
     */
    public void setImage(String imagePath) {
        byte[] dataBuffer = this.data;
        String imageTypeBuffer = imageType;
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                this.imageType = imagePath.split("\\.")[imagePath.split("\\.").length - 1];
                this.image = ImageIO.read(imageFile);
                resizeIcon();
            }
        } catch (IOException ex) {
            System.out.println("Image: '" + imagePath + "' could not get loaded");
            this.data = dataBuffer;
            this.imageType = imageTypeBuffer;
            buildImageFromData();
        }
    }

    private void buildImageFromData() {
        try {
            Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReadersByFormatName(imageType);
            while (imageReaderIterator.hasNext()) {
                ImageReader imageReader = imageReaderIterator.next();
                imageReader.setInput(new MemoryCacheImageInputStream(new ByteArrayInputStream(data)));
                this.image = imageReader.read(imageReader.getMinIndex());
            }
            resizeIcon();
        } catch (IOException ex) {
            Logger.getLogger(Icon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resizeIcon() {
        if (image != null) {
            if (image.getWidth() != ICON_WIDTH || image.getHeight() != ICON_HEIGHT) {
                BufferedImage resizedImage = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics graphics = resizedImage.createGraphics();
                graphics.drawImage(image, 0, 0, ICON_WIDTH, ICON_HEIGHT, null);
                this.setImage(resizedImage, imageType);
                graphics.dispose();
            }
            setByteArrayFromImage();
        }
    }

    private void setByteArrayFromImage() {
        if (image != null) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, imageType, outputStream);
                data = outputStream.toByteArray();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
