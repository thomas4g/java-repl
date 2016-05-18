package net.thomasshields.java_repl;

import java.util.Stack;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaProgram {
    private static final String CLASS_PREFIX = "public class ";
    private static final String CLASSNAME_PREFIX = "Class";
    private static final String EXT = ".java";
    private static final String MAIN_PREFIX = " { public static void main("
        + "String[] args) { ";
    private static final String CLASS_SUFFIX = " }}";

    private String className;
    private Stack<String> lines;
    private JavaCompiler compiler;
    private String classString;
    private File file;

    public JavaProgram() {
        className = CLASSNAME_PREFIX + this.hashCode();
        file = new File(className + EXT);
        lines = new Stack<>();
        addLine(CLASS_PREFIX + className + MAIN_PREFIX);
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    public void addLine(String line) {
        classString = toString() + line;
        lines.push(line);
    }

    public String popLine() {
        classString = null;
        return lines.pop();
    }

    private void assembleAndCompile() {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(toString(), 0, toString().length());
            fw.close();
        } catch (IOException ex) {
            //TODO: properly handle
            ex = ex;
            System.err.println("Couldn't write to file");
        }

        //TODO: hook these up
        compiler.run(null, System.out, System.err, file.getName());
    }

    public void compile() {
        addLine(CLASS_SUFFIX);
        assembleAndCompile();
        popLine();
    }

    public String toString() {
        if (classString == null) {
            StringBuilder sb = new StringBuilder();
            for (String s : lines) sb.append(s);
            classString = sb.toString();
        }
        return classString;
    }

    public String getClassName() {
        return className;
    }
}
