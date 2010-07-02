package org.jtheque.views.impl.controllers;

import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IMessageView;

import javax.annotation.Resource;

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

public class MessageController extends AbstractController {
    @Resource
    private IMessageView messageView;

    private void next() {
        messageView.next();
    }

    private void previous() {
        messageView.previous();
    }
}