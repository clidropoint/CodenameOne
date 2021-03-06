package com.codename1.samples;


import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.AreaSeries;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.RadarChart;
import com.codename1.io.Log;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class RadarChartSample {

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
    
    ChartComponent getSampleChart(){
        // Create dataset
        AreaSeries dataset = new AreaSeries();
        
        CategorySeries series1 = new CategorySeries("May");
        series1.add("Health", 0.8);
        series1.add("Attack", 0.6);
        series1.add("Defense", 0.4);
        series1.add("Critical", 0.2);
        series1.add("Speed", 1.0);
        dataset.addSeries(series1);
        
        CategorySeries series2 = new CategorySeries("Chang");
        series2.add("Health", 0.3);
        series2.add("Attack", 0.7);
        series2.add("Defense", 0.5);
        series2.add("Critical", 0.1);
        series2.add("Speed", 0.3);
        dataset.addSeries(series2);
        
        // Setup renderer
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLegendTextSize(32);
        renderer.setLabelsTextSize(24);
        renderer.setLabelsColor(ColorUtil.BLACK);
        renderer.setShowLabels(true);
        
        SimpleSeriesRenderer r1 = new SimpleSeriesRenderer();
        r1.setColor(ColorUtil.MAGENTA);
        renderer.addSeriesRenderer(r1);
        
        SimpleSeriesRenderer r2 = new SimpleSeriesRenderer();
        r2.setColor(ColorUtil.CYAN);
        renderer.addSeriesRenderer(r2);
        
        // Create chart
        return new ChartComponent(new RadarChart(dataset,renderer));
    }

    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form f = new Form("RadarChartSample", new BorderLayout());
        f.add(BorderLayout.CENTER, getSampleChart());
        f.show();
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

}
