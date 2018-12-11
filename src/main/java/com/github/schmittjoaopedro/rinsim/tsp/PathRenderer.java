package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.model.DependencyProvider;
import com.github.rinde.rinsim.core.model.ModelBuilder;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.geom.Point;
import com.github.rinde.rinsim.ui.renderers.CanvasRenderer;
import com.github.rinde.rinsim.ui.renderers.ViewPort;
import com.google.common.collect.ImmutableList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;

import java.util.Set;

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
        final Set<Vehicle> vehicles = roadModel.getObjectsOfType(Vehicle.class);
        for (final Vehicle v : vehicles) {
            for (final Parcel parcel : ImmutableList.copyOf(pdpModel.getContents(v))) {
                gc.setForeground(black);
                final Point po = parcel.getDeliveryLocation();
                final int xd = vp.toCoordX(po.x);
                final int yd = vp.toCoordY(po.y);
                gc.fillOval(xd - OVAL_RADIUS_PX, yd - OVAL_RADIUS_PX, OVAL_DIAMETER_PX, OVAL_DIAMETER_PX);
                gc.drawOval(xd - OVAL_RADIUS_PX, yd - OVAL_RADIUS_PX, OVAL_DIAMETER_PX, OVAL_DIAMETER_PX);
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
