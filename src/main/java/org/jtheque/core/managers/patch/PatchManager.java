package org.jtheque.core.managers.patch;

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

import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A manager for the patch.
 *
 * @author Baptiste Wicht
 */
public final class PatchManager implements IPatchManager {
    private final Collection<AppliedPatch> appliedPatches;
    private final Collection<OnlinePatch> onlinePatches;
    private final Collection<Patch> patches;

    private boolean updated;
    private boolean xmlUpToDate = true;

    private static final int RESTART_CODE = 2;

    /**
     * Construct a new PatchManager.
     */
    public PatchManager() {
        super();

        patches = new ArrayList<Patch>(10);
        appliedPatches = new ArrayList<AppliedPatch>(10);
        onlinePatches = new ArrayList<OnlinePatch>(10);
    }

    @Override
    public boolean isApplied(String name) {
        boolean applied = false;

        for (AppliedPatch patch : appliedPatches) {
            if (patch.getName().equals(name)) {
                applied = true;
            }
        }

        return applied;
    }

    @Override
    public void setUpdated(boolean updatedParameter) {
        updated = updatedParameter;
        xmlUpToDate = false;
    }

    @Override
    public void preInit() {
        boolean restart = applyPatchesIfNeeded();

        if (restart) {
            Managers.getManager(IViewManager.class).getSplashManager().closeSplashScreen();

            Managers.getCore().getLifeCycleManager().restart();
        }
    }

    @Override
    public void init() throws ManagerException {
        File f = new File(Managers.getCore().getFolders().getApplicationFolder(), "/core/patchs.xml");

        if (f.exists()) {
            openConfiguration(f);
        } else {
            updated = true;
            xmlUpToDate = false;
        }
    }

    /**
     * Open the configuration file of the patches.
     *
     * @param f The configuration File.
     */
    private void openConfiguration(File f) {
        XMLReader reader = new XMLReader();

        try {
            reader.openFile(f);

            readUpdated(reader);
            readAppliedPatches(reader);
            readPatches(reader);
        } catch (XMLException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Read the updated value of the configuration.
     *
     * @param reader The reader to use.
     * @throws XMLException if an exception occurs during the XML reading process.
     */
    private void readUpdated(XMLReader reader) throws XMLException {
        String strUpdated = reader.readString("updated", reader.getRootElement());

        if (strUpdated != null && "1".equals(strUpdated)) {
            updated = true;
        }
    }

    /**
     * Read the patches.
     *
     * @param reader The reader to use.
     * @throws XMLException if an exception occurs during the XML reading process.
     */
    private void readPatches(XMLReader reader) throws XMLException {
        for (Object currentNode : reader.getNodes("patch/patch", reader.getRootElement())) {
            String c = reader.readString("@class", currentNode);

            try {
                Class<?> patchClass = Class.forName(c, true, getClass().getClassLoader());

                if (Patch.class.isAssignableFrom(patchClass)) {
                    Class<Patch> castedClass = (Class<Patch>) patchClass;

                    try {
                        patches.add(castedClass.newInstance());
                    } catch (InstantiationException e) {
                        Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                    } catch (IllegalAccessException e) {
                        Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                    }
                }
            } catch (ClassNotFoundException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            }
        }
    }

    /**
     * Read the applied patches.
     *
     * @param reader The reader to use.
     * @throws XMLException if an exception occurs during the XML reading process.
     */
    private void readAppliedPatches(XMLReader reader) throws XMLException {
        for (Object currentNode : reader.getNodes("applied/patch", reader.getRootElement())) {
            AppliedPatch patch = new AppliedPatch();

            patch.setDate(new IntDate(reader.readInt("date", currentNode)));
            patch.setName(reader.readString("name", currentNode));

            appliedPatches.add(patch);
        }
    }

    @Override
    public void close() {
        if (!xmlUpToDate) {
            saveXML();
        }
    }

    /**
     * Save the XML of patches.
     */
    private void saveXML() {
        XMLWriter writer = new XMLWriter("config");

        writer.setCurrent(writer.getRoot());

        saveUpdated(writer);
        saveAppliedPatches(writer);
        savePatches(writer);

        writer.write(Managers.getCore().getFolders().getApplicationFolder().getAbsolutePath() + "/core/patchs.xml");
    }

    /**
     * Save the patches to the writer.
     *
     * @param writer The writer to use.
     */
    private void savePatches(XMLWriter writer) {
        writer.add("patchs");

        for (OnlinePatch patch : onlinePatches) {
            writer.add("patch");
            writer.addAttribute("class", patch.getClassName());
            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Save the applied patches.
     *
     * @param writer The writer to use.
     */
    private void saveAppliedPatches(XMLWriter writer) {
        writer.add("applied");

        for (AppliedPatch patch : appliedPatches) {
            writer.add("patch");

            writer.addOnly("date", Integer.toString(patch.getDate().intValue()));
            writer.addOnly("name", patch.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Save the updated flag.
     *
     * @param writer The writer to use.
     */
    private void saveUpdated(XMLWriter writer) {
        if (updated) {
            writer.addOnly("updated", "1");
        } else {
            writer.addOnly("updated", "0");
        }
    }

    /**
     * Add an applied patch.
     *
     * @param patch The patch.
     */
    private void addAppliedPatch(AppliedPatch patch) {
        appliedPatches.add(patch);
        xmlUpToDate = false;
    }

    @Override
    public void registerPatch(Patch p) {
        patches.add(p);
    }

    @Override
    public boolean applyPatchesIfNeeded() {
        boolean restart = false;

        if (updated) {
            if (applyAllNeededPatches()) {
                restart = true;
            } else {
                setUpdated(false);
                restart = false;
            }
        }

        return restart;
    }

    /**
     * Apply all the needed patches.
     *
     * @return true if we must restart else false.
     */
    private boolean applyAllNeededPatches() {
        boolean restart = false;

        for (Patch patch : patches) {
            if (!isApplied(patch.getName()) && patch.mustBeAppliedFor(Managers.getCore().getApplication().getVersion())) {
                int code = patch.apply();

                if (code < 0) {
                    registerAppliedPatch(patch);
                }

                if (code == RESTART_CODE) {//Restart
                    restart = true;
                    break;
                }
            }
        }

        return restart;
    }

    /**
     * Register an applied patch.
     *
     * @param patch The patch that has been applied.
     */
    private void registerAppliedPatch(Patch patch) {
        AppliedPatch applied = new AppliedPatch();
        applied.setDate(IntDate.today());
        applied.setName(patch.getName());

        addAppliedPatch(applied);
    }

    @Override
    public void registerOnlinePatch(OnlinePatch patch) {
        onlinePatches.add(patch);
    }
}