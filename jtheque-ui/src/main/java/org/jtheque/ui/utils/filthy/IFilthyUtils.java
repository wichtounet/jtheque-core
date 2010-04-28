package org.jtheque.ui.utils.filthy;

import org.jtheque.utils.ui.SizeTracker;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: 27 avr. 2010
 * Time: 12:25:34
 * To change this template use File | Settings | File Templates.
 */
public interface IFilthyUtils {
    Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel);
}
