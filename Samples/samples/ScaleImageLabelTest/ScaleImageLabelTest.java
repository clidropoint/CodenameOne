package com.codename1.samples;


import com.codename1.components.ScaleImageLabel;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.FlowLayout;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class ScaleImageLabelTest {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    int pressCount = 0;
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Hi World", BoxLayout.y());
        ScaleImageLabel label = new ScaleImageLabel();
        
        hi.add(new Label("Hi World"));
        Button btn = new Button("Update Label");
        btn.addActionListener(evt->{
            pressCount++;
            String message = "Pressed " + pressCount + " times";
            try {
                loadTextImage(label, label.getIcon(), message, label.getWidth(), label.getHeight());
            } catch (Exception ex) {
                Log.e(ex);
            }
        });
        hi.add(FlowLayout.encloseIn(label)).add(btn);
            
        hi.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }
    
    private void loadTextImage(ScaleImageLabel ddbox, Image image, String message, int width, int height) throws Exception
    {
        Font font = ddbox.getSelectedStyle().getFont();
        int w = font.stringWidth(message);
        int h = font.getHeight();
//Image image = null;
        Graphics graphics = null;
        if (width <= w) {
            if (height <= h) {
                if (image == null) {
                    image = Image.createImage(w, h);
                } else {
                    height = image.getHeight();
                    width = image.getWidth();
                }
                graphics = image.getGraphics();
                graphics.setFont(font);
                graphics.setColor(ddbox.getSelectedStyle().getFgColor());
                graphics.drawString(message, 0, 0);
            } else {
                if (image == null) {
                    image = Image.createImage(w, height);
                } else {
                    height = image.getHeight();
                    width = image.getWidth();
                }
                graphics = image.getGraphics();
                graphics.setFont(font);
//graphics.setColor(ddbox.getSelectedStyle().getFgColor());
                graphics.setColor(255);
                graphics.drawString(message, 0, (height / 2) - (h / 2));
            }
        } else if (height <= h) {
            if (image == null) {
                image = Image.createImage(width, h);
            } else {
                height = image.getHeight();
                width = image.getWidth();
            }
            graphics = image.getGraphics();
            graphics.setFont(font);
            graphics.setColor(ddbox.getSelectedStyle().getFgColor());
            graphics.drawString(message, (width / 2) - (w / 2), 0);
        } else {
            if (image == null) {
                image = Image.createImage(width, height);
            } else {
                height = image.getHeight();
                width = image.getWidth();
            }
            graphics = image.getGraphics();
            graphics.setFont(font);
            graphics.setColor(ddbox.getSelectedStyle().getFgColor());
            graphics.drawString(message, (width / 2) - (w / 2), (height / 2) - (h / 2));
        }
        ddbox.setIcon(image);
        final Container parent = ddbox.getParent();
        if (parent != null) {
            parent.revalidateWithAnimationSafety();
//Display.getInstance().callSerially(() -> parent.revalidate());
        }

    }

}
