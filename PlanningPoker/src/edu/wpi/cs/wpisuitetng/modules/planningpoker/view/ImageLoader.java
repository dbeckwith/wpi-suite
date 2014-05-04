/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Loads images given the file name keeping track of loaded images so that they
 * don't need to be loaded multiple times.
 * 
 * @author Team 9
 * @version 1.0
 * 
 */
public class ImageLoader {
    
    private static final String modifiedPath = "/res/";
    
    private static HashMap<String, BufferedImage> images;
    
    static {
        ImageLoader.images = new HashMap<String, BufferedImage>();
    }
    
    /**
     * Returns a BufferedImage loaded from a file
     * 
     * @param file
     *        the name of the file to load from the resources folder
     * @return The BufferedImage from file
     */
    public static BufferedImage getImage(String file) {
        BufferedImage bImg = null;
        
        //check if the image is already loaded
        if (ImageLoader.images.containsKey(file)) {
            bImg = ImageLoader.images.get(file); //get the image from the list
        } else {
            try {
                final URL u = ImageLoader.class.getResource(ImageLoader.modifiedPath + file);
                bImg = ImageIO.read(ImageLoader.class.getResource(ImageLoader.modifiedPath + file));
                if (bImg != null) {
                	//put the image in the list
                    ImageLoader.images.put(file, bImg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bImg;
    }
    
    /**
     * Returns an ImageIcon loaded from a file
     * 
     * @param file
     *        the name of the file to load from the resources folder
     * @return The ImageIcon from file
     */
    public static ImageIcon getIcon(String file) {
        ImageIcon icon = null;
        final BufferedImage image = ImageLoader.getImage(file);
        if (image != null) {
            icon = new ImageIcon(image);
        }
        return icon;
    }
    
}
