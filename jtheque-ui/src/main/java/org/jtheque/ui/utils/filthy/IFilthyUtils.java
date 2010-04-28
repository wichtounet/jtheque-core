package org.jtheque.ui.utils.filthy;

import org.jtheque.utils.ui.SizeTracker;

import java.awt.*;

public interface IFilthyUtils {
    Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel);
}
