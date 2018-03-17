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
 * Roses are red,
 * It's a simple fix
 * Believe it or not,
 * Arrays start at six.
 *
 *-----------------------------------------------------------------------------
 * ContainerLG.java
 *-----------------------------------------------------------------------------
 * The basis/template for all UI objects inheriting ContainerLG.
 * All inherited object are placed in zone 2.
 *-----------------------------------------------------------------------------
 */

package ui;

import javax.swing.*;
import java.awt.*;

class ContainerLG extends JPanel {

    private static final int panel_width_contracted = 725;
    private static final int panel_height_contracted = 420;

    /**
     * Default Constructor
     */
    ContainerLG() {

        setBounds(380,20, panel_width_contracted ,panel_height_contracted);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);

    }

}
