package org.jtheque.messages;

import org.jtheque.messages.impl.Messages;
import org.jtheque.utils.bean.IntDate;

import org.junit.Test;

import static org.junit.Assert.*;

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

public class MessagesTest {
    @Test
    public void testCommonMethods(){
        Message message1 = Messages.newEmptyTodayMessage(1);
        Message message2 = Messages.newEmptyTodayMessage(1);

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());

        IntDate yesterday = IntDate.today();
        yesterday.add(IntDate.Fields.DAY, -1);

        message1 = Messages.newMessage(1, "title", "message", IntDate.today(), "source");
        message2 = Messages.newMessage(1, "title", "message", yesterday, "source");

        assertFalse(message1.equals(message2));
        assertTrue(message1.compareTo(message2) > 0);
    }

    @Test
    public void testConstructor() {
        IntDate date = new IntDate(20101010);

        Message message = Messages.newMessage(1, "title", "message", date, "source");

        assertEquals(date, message.getDate());
        assertEquals("title", message.getTitle());
        assertEquals(1, message.getId());
        assertEquals("message", message.getText());
        assertEquals("source", message.getSource());
    }
}