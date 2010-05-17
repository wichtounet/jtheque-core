package org.jtheque.ui.utils.filthy;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.ui.able.Filthy;
import org.jtheque.ui.able.IFilthyUtils;
import org.jtheque.utils.ui.SizeTracker;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * A panel with a filthy background.
 *
 * @author Baptiste Wicht
 */
public class FilthyBackgroundPanel extends JPanel implements Filthy {
    private final SizeTracker tracker = new SizeTracker(this);

    private IFilthyUtils filthyUtils;
    private Image gradientImage;

	public FilthyBackgroundPanel() {
		super();
	}

	public FilthyBackgroundPanel(IFilthyUtils filthyUtils) {
        super();

        this.filthyUtils = filthyUtils;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        gradientImage = getFilthyUtils().paintFilthyBackground(g, gradientImage, tracker, this);
    }

	protected IFilthyUtils getFilthyUtils(){
		return filthyUtils;
	}
}