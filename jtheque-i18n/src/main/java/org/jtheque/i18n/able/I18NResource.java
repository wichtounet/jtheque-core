package org.jtheque.i18n.able;

import org.springframework.core.io.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: wichtounet
 * Date: May 15, 2010
 * Time: 12:33:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface I18NResource {
	String getFileName();

	Resource getResource();
}
