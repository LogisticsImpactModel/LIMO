package nl.fontys.sofa.limo.domain;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Icon extends BaseEntity implements Serializable {

    private byte[] iconByteArray;
    private transient BufferedImage iconBufferedImage = null;

    /**
     * Creates a new Icon with an empty bytes array.
     */
    public Icon() {
        super();
        this.iconByteArray = new byte[]{};
    }

    /**
     * Creates a new Icon with an already existing byte array.
     *
     * @param iconByteArray The byte array that should be used.
     */
    public Icon(byte[] iconByteArray) {
        this.iconByteArray = iconByteArray;
        getIcon();
    }

    /**
     * Creates a new Icon with an image.
     *
     * @param image The image that should be used to create this icon.
     */
    public Icon(Image image) {
        setIcon(image);
    }

    /**
     * Creates a new icon with an existing image. Therefore it reads an image
     * from a specified path. When the path is wrong the byte array will set
     * empty.
     *
     * @param iconPath The path where of the icon.
     */
    public Icon(String iconPath) {
        setImage(iconPath);
    }

    /**
     * Returns the Icon as an Image.
     *
     * @return The Icon as Image.
     */
    public final Image getIcon() {
        if (iconBufferedImage == null) {
            try {
                iconBufferedImage = ImageIO.read(new ByteArrayInputStream(iconByteArray));
            } catch (IOException ex) {
                Logger.getLogger(Icon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return iconBufferedImage;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public byte[] getIconByteArray() {
        return iconByteArray;
    }

    public void setIconByteArray(byte[] iconByteArray) {
        this.iconByteArray = iconByteArray;
        getIcon();
    }

    public BufferedImage getIconBufferedImage() {
        return iconBufferedImage;
    }

    public void setIconBufferedImage(BufferedImage iconBufferedImage) {
        setIcon(iconBufferedImage);
    }
    // </editor-fold>
    
    /**
     * Sets the icon to a specified image.
     *
     * @param image The image that should be used.
     */
    public final void setIcon(Image image) {
        if (!(image instanceof BufferedImage)) {
            iconBufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D bGr = iconBufferedImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
        } else {
            iconBufferedImage = (BufferedImage) image;
        }
        WritableRaster raser = iconBufferedImage.getRaster();
        DataBufferByte dataBufferArray = (DataBufferByte) raser.getDataBuffer();
        this.iconByteArray = dataBufferArray.getData();
    }

    /**
     * Sets the icon to the one from a specified path. If the path does not
     * exist the icon will not get set and the old one will be restored.
     *
     * @param iconPath The path to the new Icon.
     */
    public final void setImage(String iconPath) {
        byte[] buffer = iconByteArray;
        try {
            File imageFile = new File(iconPath);
            iconBufferedImage = ImageIO.read(imageFile);
            WritableRaster raster = iconBufferedImage.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            iconByteArray = data.getData();
        } catch (IOException ex) {
            System.out.println("Image: '" + iconPath + "' could not get loaded");
            if (buffer == null) {
                this.iconByteArray = new byte[]{};
            } else {
                this.iconByteArray = buffer;
            }
            iconBufferedImage = null;
        }
    }
}