package org.jtheque.file.able;

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
 * A Backup Reader. It seems an object who read a backup file and persist the data from the file.
 *
 * @author Baptiste Wicht
 */
public interface ModuleBackuper {
    /**
     * Return the name of the schema.
     *
     * @return The name of the schema.
     */
    String getId();

    /**
     * Return all the dependencies of the schema.
     *
     * @return An array containing all the dependencies of the schema.
     */
    String[] getDependencies();

    /**
     * Backup the module data.
     *
     * @return The backup of the module data. 
     */
    ModuleBackup backup();

	/**
	 * Restore the specified ModuleBackup.
	 *
	 * @param backup The backup to restore. 
	 */
    void restore(ModuleBackup backup);
}