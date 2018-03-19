/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * Starting a for loop at i = 1. *Wince
 *
 *-----------------------------------------------------------------------------
 * StateButton.java
 *-----------------------------------------------------------------------------
 * A ToggleButton that do not have a MouseListener.
 *-----------------------------------------------------------------------------
 */

package ui;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

class StateButton extends ToggleButton {

    /**
     * Constructor
     * all paraemter below are the same as toggle button
     * @param icon
     * @param num_states
     * @param x
     * @param y
     * @param width
     * @param height
     */
    StateButton (BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        // construct toggle button and remove mouselisteners
        super(icon, num_states, x, y, width, height);

        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
    }

}
