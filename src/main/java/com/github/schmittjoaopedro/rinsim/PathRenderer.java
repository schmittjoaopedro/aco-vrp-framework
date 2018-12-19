package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.DependencyProvider;
import com.github.rinde.rinsim.core.model.ModelBuilder;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.geom.Point;
import com.github.rinde.rinsim.ui.renderers.CanvasRenderer;
import com.github.rinde.rinsim.ui.renderers.ViewPort;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;

import java.util.List;

public class PathRenderer extends CanvasRenderer.AbstractCanvasRenderer {

    RoadModel roadModel;
    PDPModel pdpModel;
    private static final int OVAL_RADIUS_PX = 2;
    private static final int OVAL_DIAMETER_PX = 2 * OVAL_RADIUS_PX;
    private final Color foregroundInfo;
    private final Color backgroundInfo;
    private final Color black;

    PathRenderer(RoadModel rm, PDPModel pm, Device d) {
        roadModel = rm;
        pdpModel = pm;
        black = d.getSystemColor(SWT.COLOR_BLACK);
        foregroundInfo = d.getSystemColor(SWT.COLOR_WHITE);
        backgroundInfo = d.getSystemColor(SWT.COLOR_BLUE);
    }

    @Override
    public void renderStatic(GC gc, ViewPort vp) {
    }

    @Override
    public void renderDynamic(GC gc, ViewPort vp, long time) {
        int xPrev;
        int yPrev;
        int xCurr;
        int yCurr;
        int xDept;
        int yDept;
        Point parcelPosition;
        Point depotPosition;
        List<Parcel> route;
        Parcel parcel;
        for (final Salesman salesmen : roadModel.getObjectsOfType(Salesman.class)) {
            xPrev = 0;
            yPrev = 0;
            route = salesmen.getRoute();
            depotPosition = roadModel.getPosition(salesmen.getDepot());
            xDept = vp.toCoordX((int) depotPosition.x);
            yDept = vp.toCoordY((int) depotPosition.y);
            // Draw route parcel locations and line connecting them
            for (int i = 0; i < route.size(); i++) {
                parcel = route.get(i);
                gc.setForeground(black);
                parcelPosition = parcel.getDeliveryLocation();
                xCurr = vp.toCoordX(parcelPosition.x);
                yCurr = vp.toCoordY(parcelPosition.y);
                gc.fillOval(xCurr - OVAL_RADIUS_PX, yCurr - OVAL_RADIUS_PX, OVAL_DIAMETER_PX, OVAL_DIAMETER_PX);
                gc.drawOval(xCurr - OVAL_RADIUS_PX, yCurr - OVAL_RADIUS_PX, OVAL_DIAMETER_PX, OVAL_DIAMETER_PX);
                if (xPrev != 0 && yPrev != 0) {
                    gc.drawLine(xPrev, yPrev, xCurr, yCurr);
                }
                xPrev = xCurr;
                yPrev = yCurr;
                if (i == 0) { // If is the next parcel related to depot
                    gc.drawLine(xDept, yDept, xCurr, yCurr);
                } else if (i == route.size() - 1) { // If is the prev parcel related to depot
                    gc.drawLine(xCurr, yCurr, xDept, yDept);
                }
            }
            gc.setBackground(backgroundInfo);
            gc.setForeground(foregroundInfo);
        }
    }

    public static PathRenderer.Builder builder() {
        return new PathRenderer.Builder();
    }

    public static class Builder extends ModelBuilder.AbstractModelBuilder<PathRenderer, Void> {

        private static final long serialVersionUID = 2467340977162967147L;

        Builder() {
            setDependencies(RoadModel.class, PDPModel.class, Device.class);
        }

        @Override
        public PathRenderer build(DependencyProvider dependencyProvider) {
            final RoadModel rm = dependencyProvider.get(RoadModel.class);
            final PDPModel pm = dependencyProvider.get(PDPModel.class);
            final Device d = dependencyProvider.get(Device.class);
            return new PathRenderer(rm, pm, d);
        }
    }
}
