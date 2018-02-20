package com.leetbot;

import com.leetbot.api.API;
import com.leetbot.commons.config.Configurations;
import com.leetbot.python.model.TestModel;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.util.Properties;

/**
 * ${FILE_NAME}
 *
 * @author Notorious
 * @version 1.0.0
 * @since 2/20/2018
 */
public class Bridge {

    private API api;
    private PyObject testClass;

    public Bridge() {
        String value = new File(Configurations.INSTANCE.rootDirectory(), "python/scripts").getAbsolutePath()
                .replace('\\', '/');
        System.out.println(value);
        Properties p = new Properties();
        PythonInterpreter.initialize(System.getProperties(), p, new String[]{ "test"});
        p.setProperty("python.path", value); // Sets the module path
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(value + "/test.py");
        testClass = interpreter.get("Test");
    }

    public TestModel create () {
        PyObject buildingObject = testClass.__call__();
        return (TestModel)buildingObject.__tojava__(TestModel.class);
    }
}
