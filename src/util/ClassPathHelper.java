package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: inkblot
 * Date: 10/24/11
 * Time: 7:23 PM
 */
public class ClassPathHelper {
    public static String classPath() {
        return classPath(".");
    }

    public static String classPath(String path) {
        StringBuilder cp = new StringBuilder();

        cp.append(classPathDir(new File(path), "build/test/classes"));
        cp.append(classPathDir(new File(path), "test-resources"));
        cp.append(classPathDir(new File(path), "build/classes"));
        cp.append(classPathDir(new File(path), "resources"));
        cp.append(classPathJarDir(new File(path), "lib/runtime"));
        cp.append(classPathJarDir(new File(path), "lib/compile"));
        cp.append(classPathJarDir(new File(path), "lib/test"));

        return cp.toString().replaceAll(Pattern.quote(File.pathSeparator) + "$", "");
    }

    public static String classPathDir(File basedir, String path) {
        File dirFile = new File(basedir, path);
        if (dirFile.exists())
            return dirFile.getAbsolutePath() + File.pathSeparator;
        return "";
    }

    public static StringBuilder classPathJarDir(File basedir, String path) {
        final File jarDirFile = new File(basedir, path);
        StringBuilder dirClassPath = new StringBuilder();
        if (jarDirFile.exists()) {
            File[] jarList = jarDirFile.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return dir.equals(jarDirFile) && name.endsWith(".jar");
                }
            });
            for (File aJarList : jarList) {
                assertTrue(aJarList.exists());
                dirClassPath.append(aJarList.getAbsolutePath()).append(File.pathSeparator);
            }
        }
        return dirClassPath;
    }
}
