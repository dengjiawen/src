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
 * // life motto
 * if (sad() == true) {
 *     sad.stop();
 *     beAwesome();
 * }
 *
 *-----------------------------------------------------------------------------
 * NegotiablePanel.java
 *-----------------------------------------------------------------------------
 * An interface for major panels that inhabits zone 1, 2 and 3.
 *-----------------------------------------------------------------------------
 */

package ui;

interface NegotiablePanel {

    /**
     * Method for updating the invoker of the panel
     * (eg. the toggle button that triggered the panel).
     * For example, if music panel is triggered, the music
     * icon would change to a white color.
     * If the music panel is closed, this method would
     * change the music icon back to its default color (grey).
     */
    void updateInvoker ();

    /**
     * Set visible method, usually present with all JComponents
     * @param is_visible
     */
    void setVisible (boolean is_visible);

    /**
     * Any other code that needs to be executed before the
     * panel is shown (eg. start timer, start animation,
     * or refreshing the UI, etc.)
     * @param is_active
     */
    void setActive (boolean is_active);

}
