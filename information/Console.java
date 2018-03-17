/**
 * Copyright 2018 (C) Jiawen Deng. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng. (I-LU-V-EH)
 *
 * Roses are red,
 * Violets are blue.
 * Unexpected '{'
 * On line 32.
 *
 *-----------------------------------------------------------------------------
 * Console.java
 *-----------------------------------------------------------------------------
 * This is a specialized java class designed to output debug information onto
 * the console. (Designed and written by Jiawen Deng)
 *-----------------------------------------------------------------------------
 */

package information;

import sun.reflect.Reflection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {

    //Constant boolean that toggles messages on/off.
    private static final boolean show_debug_messages = true;
    private static final boolean show_general_messages = true;
    private static final boolean show_repaint_messages = false;

    /**
     * Prints a generic message to console, including time and the
     * originating class name.
     * @param message     message to be printed
     */
    public static void printGeneralMessage (String message) {
        if (show_general_messages) System.out.println(getDate() + " -> |" +  Reflection.getCallerClass(2) + "| " + message);
    }

    /**
     * Prints a message to console with the DEBUG tag, including time and
     * the originating class name.
     * @param message     message to be printed
     */
    public static void printDebugMessage (String message) {
        if (show_debug_messages) System.out.println(getDate() + " -> |" +  Reflection.getCallerClass(2) + "| " + " DEBUG " + message);
    }

    /**
     * Prints a message to console with the ERROR tag, including time and
     * the originating class name.
     * @param message     message to be printed
     */
    public static void printErrorMessage (String message) {
        System.out.println(getDate() + " -> |" +  Reflection.getCallerClass(2) + "| " + " ERROR " + message);
    }

    public static void printRepaintMessage () {
        if (show_repaint_messages) System.out.println(getDate() + " -> |" + Reflection.getCallerClass(2) + "|" + " REPAINT EVENT: Invoked successfully.");
    }


    /**
     * Prints a generic message to console, including time.
     * DEPRECATED CODE | USE printGeneralMessage
     * @param message     message to be printed
     */
    @Deprecated
    public static void print (String message) {
        System.out.println(getDate() + " -> " + message +
                "\nConsole.print() had been deprecated. Use Console.printGeneralMessage() instead.");
    }

    /**
     * Gets the date from system for console output.
     * @return    string representation of current time
     */
    private static String getDate () {
        DateFormat date_format = new SimpleDateFormat("HH:mm:ss:SSS");
        Date date_object = new Date();

        return date_format.format(date_object);
    }

}