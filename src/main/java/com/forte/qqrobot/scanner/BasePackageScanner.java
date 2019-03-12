package com.forte.qqrobot.scanner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author https://blog.csdn.net/weixin_41978722/article/details/82929397
 */
public abstract class BasePackageScanner {

	public BasePackageScanner() {
	}

	public abstract void dealClass(Class<?> klass);

	public void packageScanner(Class<?> klass) {
		packageScanner(klass.getPackage().getName());
	}

	public void packageScanner(String packageName) {
		String packagePath = packageName.replace(".", "/");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			Enumeration<URL> resources = classLoader.getResources(packagePath);
			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				if (url.getProtocol().equals("jar")) {
					scanPackage(url);
				} else {
					File file = new File(url.toURI());
					if (!file.exists()) {
						continue;
					}
					scanPackage(packageName, file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void scanPackage(URL url) throws IOException {
		JarURLConnection jarUrlConnection =  (JarURLConnection) url.openConnection();
		JarFile jarFile = jarUrlConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();

		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			String jarName = jarEntry.getName();
			if (jarEntry.isDirectory() || !jarName.endsWith(".class")) {
				continue;
			}
			String className = jarName.replace(".class", "").replaceAll("/", ".");
			try {
				Class<?> klass = Class.forName(className);
				if (klass.isAnnotation()
						||klass.isInterface()
						||klass.isEnum()
						||klass.isPrimitive()) {
					continue;
				}
				dealClass(klass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void scanPackage(String packageName, File currentfile) {
		File[] files = currentfile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (currentfile.isDirectory()) {
					return true;
				}
				return pathname.getName().endsWith(".class");
			}
		});
		for (File file : files) {
			if (file.isDirectory()) {
				scanPackage(packageName + "." + file.getName(), file);
			} else {
				String fileName = file.getName().replace(".class", "");
				String className = packageName + "." + fileName;
				try {
					Class<?> klass = Class.forName(className);
					if (klass.isAnnotation()
							||klass.isInterface()
							||klass.isEnum()
							||klass.isPrimitive()) {
						continue;
					}
					dealClass(klass);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

	}
}