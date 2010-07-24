package org.jtheque.ui.utils;

import org.jtheque.ui.able.IModel;
import org.jtheque.ui.able.IView;
import org.jtheque.utils.ui.SwingUtils;

import org.jdesktop.swingx.JXPanel;

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

/**
 * An abstract panel view. This is a panel that can be using as a view in JTheque.
 *
 * @author Baptiste Wicht
 */
public class AbstractPanelView extends JXPanel implements IView {
    @Override
    public void display() {
        throw new UnsupportedOperationException("Not implemented on panels");
    }

    @Override
    public void closeDown() {
        throw new UnsupportedOperationException("Not implemented on panels");
    }

    @Override
    public void toFirstPlan() {
        throw new UnsupportedOperationException("Not implemented on panels");
    }

    @Override
    public void sendMessage(String message, Object value) {
        throw new UnsupportedOperationException("Not implemented by default");
    }

    @Override
    public void refresh() {
        SwingUtils.refresh(this);
    }

    @Override
    public IModel getModel() {
        return null;
    }

    @Override
    public boolean validateContent() {
        return true;
    }

    @Override
    public Object getImpl() {
        return this;
    }
}