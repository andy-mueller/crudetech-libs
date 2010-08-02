////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.graphics2d.awt;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.*;
import com.crudetech.graphics2d.xwt.Color;

import javax.swing.*;
import java.awt.*;


class SimpleFrame extends JFrame {
    public static void main(String[] args) {
        final SimpleFrame mainFrame = new SimpleFrame();
        mainFrame.setSize(500, 600);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                mainFrame.setVisible(true);
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        GraphicsStream2d pipe = new GraphicsStream2d(new AwtGraphicsContext((Graphics2D) graphics));

        pipe.getCoordinateSystemStack().pushTranslation(100, 100);
        pipe.getCoordinateSystemStack().pushRotationInRadians(RadianAngles.k30);

        pipe.getPenStack().push(new Pen(3, Cap.Square, Join.Miter, 1, null, 0));
        
        pipe.getBrushStack().push(new SolidBrush(Color.Blue));
        pipe.drawLine(new Point2d(10, 100), new Point2d(200, 100));
    }
}
