package org.jtheque.osgi;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.DemuxOutputStream;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;

import java.io.PrintStream;

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

public class JTheque {

    public static void main(String[] args) {
        if (args.length > 0) {
            System.setProperty("user.dir", args[0]);
        }

        launch();
    }

    private static void launch() {
        int ret = launchJTheque();

        if(ret > 0){
            launch();
        }
    }

    private static int launchJTheque() {
        int returnCode;

        Thread.currentThread().setName("Kernel-MainThread");

        Project project = new Project();

        project.setBasedir(System.getProperty("user.dir"));
        project.init();

        PrintStream out = System.out;
        PrintStream err = System.err;

        BuildLogger logger = new DefaultLogger();
        logger.setOutputPrintStream(out);
        logger.setErrorPrintStream(err);
        logger.setMessageOutputLevel(Project.MSG_INFO);

        project.addBuildListener(logger);

        System.setOut(new PrintStream(new DemuxOutputStream(project, false)));
        System.setErr(new PrintStream(new DemuxOutputStream(project, true)));

        project.fireBuildStarted();

        Throwable caught = null;
        try {
            project.log("Launch JTheque Kernel");

            Java javaTask = new Java();
            javaTask.setTaskName("JTheque");
            javaTask.setProject(project);
            javaTask.setFork(true);
            javaTask.setFailonerror(true);
            javaTask.setCloneVm(true);
            javaTask.setClassname(Kernel.class.getName());
            javaTask.init();

            returnCode = javaTask.executeJava();
        } catch (BuildException e) {
            caught = e;

            returnCode = -1;
        }

        project.fireBuildFinished(caught);

        System.setOut(out);
        System.setErr(err);

        return returnCode;
    }
}