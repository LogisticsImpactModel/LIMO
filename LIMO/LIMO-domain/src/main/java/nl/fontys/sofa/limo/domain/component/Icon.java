package nl.fontys.sofa.limo.domain.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * An icon used by hubs and legs.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Icon implements Serializable {

    private byte[] data;
    private transient BufferedImage image;

    /*
     CONSTRUCTORS
     */
    public Icon() {
        data = new byte[]{};
        this.buildImageFromData();
        this.setIconSize();
    }

    public Icon(byte[] data) {
        this.data = data;
        this.buildImageFromData();
        this.setIconSize();
    }

    public Icon(Image image) {
        this.setImage(image);
        this.setIconSize();
    }

    /*
     GETTERS
     */
    public byte[] getData() {
        return data;
    }

    public BufferedImage getImage() {
        if (this.image == null) {
            this.buildImageFromData();
        }
        return this.image;
    }

    /*
     SETTERS
     */
    public void setData(byte[] data) {
        this.data = data;
        this.image = null;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        this.data = ((DataBufferByte) image.getData().getDataBuffer()).getData();
    }

    /**
     * Set icon by image.
     *
     * @param image New icon image.
     */
    public final void setImage(Image image) {
        if (!(this.image instanceof BufferedImage)) {
            this.image = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D bGr = this.image.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
        } else {
            this.image = (BufferedImage) image;
        }
        Raster raster = this.image.getData();
        DataBufferByte dataBufferArray = (DataBufferByte) raster.getDataBuffer();
        this.data = dataBufferArray.getData();
    }

    /**
     * Set icon by path. Old one will not change upon error.
     *
     * @param path Path to new icon.
     */
    public void setImage(String path) {
        byte[] buffer = this.data;
        try {
            File imageFile = new File(path);
            this.image = ImageIO.read(imageFile);
            Raster raster = image.getData();
            DataBufferByte imgData = (DataBufferByte) raster.getDataBuffer();
            this.data = imgData.getData();
        } catch (IOException ex) {
            System.out.println("Image: '" + path + "' could not get loaded");
            if (buffer == null) {
                this.data = new byte[]{};
            } else {
                this.data = buffer;
            }
            image = null;
        }
    }

    private void buildImageFromData() {
        try {
            this.image = ImageIO.read(new ByteArrayInputStream(this.data));
        } catch (IOException ex) {
            Logger.getLogger(Icon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setIconSize() {
        if (image != null) {
            if (image.getWidth() != 64 || image.getHeight() != 64) {
                BufferedImage resizedImage = new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics g = resizedImage.createGraphics();
                g.drawImage(image, 0, 0, 64, 64, null);
                this.setImage(resizedImage);
                g.dispose();
            }
        }
    }
}
