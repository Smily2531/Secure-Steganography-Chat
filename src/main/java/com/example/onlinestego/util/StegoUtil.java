package com.example.onlinestego.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class StegoUtil {

    public static void encodeText(File inputImage, File outputImage, String message) throws IOException {
        BufferedImage img = ImageIO.read(inputImage);
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        int dataLen = data.length;
        int w = img.getWidth(), h = img.getHeight();
        int capacity = w*h;
        if(dataLen + 4 > capacity) throw new IllegalArgumentException("Message too large for image");

        byte[] all = new byte[4 + dataLen];
        all[0] = (byte)((dataLen >> 24) & 0xFF);
        all[1] = (byte)((dataLen >> 16) & 0xFF);
        all[2] = (byte)((dataLen >> 8) & 0xFF);
        all[3] = (byte)(dataLen & 0xFF);
        System.arraycopy(data,0,all,4,dataLen);

        int idx = 0;
        outer:
        for (int y=0;y<h;y++){
            for (int x=0;x<w;x++){
                int rgb = img.getRGB(x,y);
                int blue = rgb & 0xFF;
                int bit = (all[idx/8] >> (7 - (idx%8))) & 1;
                blue = (blue & 0xFE) | bit;
                int newRgb = (rgb & 0xFFFFFF00) | blue;
                img.setRGB(x,y,newRgb);
                idx++;
                if(idx >= all.length*8) break outer;
            }
        }
        ImageIO.write(img, "png", outputImage);
    }

    public static String decodeText(File imageFile) throws IOException {
        BufferedImage img = ImageIO.read(imageFile);
        int w = img.getWidth(), h = img.getHeight();
        int capacity = w * h;
        
        if (capacity < 4) {
            System.out.println("Image too small");
            return null;
        }
        
        // Read length first (32 bits)
        byte[] lenBytes = new byte[4];
        int idx = 0;
        outer:
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int blue = img.getRGB(x, y) & 0xFF;
                int bit = blue & 1;
                lenBytes[idx / 8] = (byte) ((lenBytes[idx / 8] << 1) | bit);
                idx++;
                if (idx >= 32) break outer;
            }
        }
        
        // Convert bytes to length
        int dataLen = ((lenBytes[0] & 0xFF) << 24) | 
                     ((lenBytes[1] & 0xFF) << 16) | 
                     ((lenBytes[2] & 0xFF) << 8) | 
                     (lenBytes[3] & 0xFF);
        
        System.out.println("Image size: " + w + "x" + h + ", capacity: " + capacity);
        System.out.println("Decoded length: " + dataLen);
        
        if (dataLen <= 0 || dataLen > capacity - 4 || dataLen > 100000) {
            System.out.println("Invalid dataLen: " + dataLen);
            return null; // No valid message
        }
        
        // Read the actual message data
        byte[] data = new byte[dataLen];
        idx = 0; // Bit index
        int byteIdx = 0; // Byte position in data array
        
        outer:
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (idx < 32) {
                    idx++; // Skip length bits
                    continue;
                }
                
                if (idx >= (4 + dataLen) * 8) {
                    break outer;
                }
                
                int blue = img.getRGB(x, y) & 0xFF;
                int bit = blue & 1;
                
                // Extract the bit from the correct position in the byte
                int bytePosition = (idx - 32) / 8;
                int bitPosition = (idx - 32) % 8;
                
                data[bytePosition] = (byte) (data[bytePosition] | (bit << (7 - bitPosition)));
                
                idx++;
                
                if (idx >= (4 + dataLen) * 8) {
                    break outer;
                }
            }
        }
        
        if (byteIdx == 0 && dataLen > 0) {
            // Read at least some data
            String result = new String(data, 0, dataLen, StandardCharsets.UTF_8);
            System.out.println("Decoded message: " + result);
            return result;
        }
        
        System.out.println("No data read");
        return null;
    }
}
