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
 * Programmer while coding:
 * "It doesn't work. Why?"
 * 30 seconds later
 * "OK, now it works. Why?"
 *
 *-----------------------------------------------------------------------------
 * ModifiedToggleButton.java
 *-----------------------------------------------------------------------------
 * A toggle button that does not have any mouseListeners.
 * (DEPRECATED, replaced by StateButton)
 *-----------------------------------------------------------------------------
 */

package ui;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

@Deprecated
class ModifiedToggleButton extends ToggleButton {

    @Deprecated
    ModifiedToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        super(icon, num_states, x, y, width, height);

        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }

    }

}
