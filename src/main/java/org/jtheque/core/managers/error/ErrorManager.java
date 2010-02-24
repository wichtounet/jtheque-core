package org.jtheque.core.managers.error;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.view.able.IViewManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An error manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ErrorManager extends AbstractManager implements IErrorManager {
    private static final Collection<JThequeError> STARTUP_ERRORS = new ArrayList<JThequeError>(10);

    private final Collection<JThequeError> errors = new ArrayList<JThequeError>(10);

    @Resource
    private IViewManager viewManager;

    @Override
    public void init(){
        errors.addAll(STARTUP_ERRORS);

        STARTUP_ERRORS.clear();
    }

    @Override
    public void addError(JThequeError error) {
        errors.add(error);

        viewManager.getDelegate().displayError(error);
    }

    @Override
    public void addStartupError(JThequeError error) {
        STARTUP_ERRORS.add(error);
    }

    @Override
    public void displayErrors() {
        for (JThequeError error : errors) {
            viewManager.getDelegate().displayError(error);
        }
    }

    @Override
    public void addInternationalizedError(String messageKey) {
        addError(new InternationalizedError(messageKey));
    }

    @Override
    public void addInternationalizedError(String messageKey, Object... messageReplaces) {
        addError(new InternationalizedError(messageKey, messageReplaces));
    }
}