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

import com.crudetech.graphics2d.xwt.GraphicsStream2d;
import com.crudetech.graphics2d.xwt.test.TestScene;

import javax.swing.*;
import java.awt.*;


class SimpleFrame extends JFrame {

    public static void main(String[] args) {
        final SimpleFrame mainFrame = new SimpleFrame();
        mainFrame.setSize(500, 600);

        mainFrame.setRootPane(new JRootPane() {
        });

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                mainFrame.setVisible(true);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;

        graphics.translate(25, 40);

        TestScene scene = new TestScene(getWidth() - 2*25, getHeight() - 2*40);

        GraphicsStream2d pipe = new GraphicsStream2d(new AwtGraphicsContext((Graphics2D) graphics));
        scene.render(pipe);
    }
}
