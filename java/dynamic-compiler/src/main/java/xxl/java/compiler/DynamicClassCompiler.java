package xxl.java.compiler;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.File;
import java.net.URL;
import java.util.*;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class DynamicClassCompiler {

	public DynamicClassCompiler(URL[] classpath) {
		this();
		options = optionsWithClasspath(classpath);
	}

	public DynamicClassCompiler() {
		options = asList("-nowarn");
		compiler = ToolProvider.getSystemJavaCompiler();
		diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager standardFileManager = compiler().getStandardFileManager(diagnostics(), null, null);
		fileManager = new VirtualFileObjectManager(standardFileManager);
	}

	public synchronized byte[] javaBytecodeFor(String qualifiedName, String sourceContent) {
		return javaBytecodeFor(qualifiedName, sourceContent, new HashMap<String, byte[]>());
	}

	public synchronized byte[] javaBytecodeFor(String qualifiedName, String sourceContent,
			Map<String, byte[]> compiledDependencies) {
		Map<String, String> adHocMap = new HashMap<String, String>();
		adHocMap.put(qualifiedName, sourceContent);
		return javaBytecodeFor(adHocMap, compiledDependencies).get(qualifiedName);
	}

	public synchronized Map<String, byte[]> javaBytecodeFor(Map<String, String> qualifiedNameAndContent) {
		return javaBytecodeFor(qualifiedNameAndContent, new HashMap<String, byte[]>());
	}

	public synchronized Map<String, byte[]> javaBytecodeFor(Map<String, String> qualifiedNameAndContent,
			Map<String, byte[]> compiledDependencies) {
		log(format("[Compiling %d source files]", qualifiedNameAndContent.size()));
		Collection<JavaFileObject> units = addCompilationUnits(qualifiedNameAndContent);
		fileManager().addCompiledClasses(compiledDependencies);
		CompilationTask task = compiler().getTask(null, fileManager(), diagnostics(), options(), null, units);
		runCompilationTask(task);
		Map<String, byte[]> bytecodes = collectBytecodes(qualifiedNameAndContent);
		log(format("[Compilation finished successfully (%d classes)]", bytecodes.size()));
		return bytecodes;
	}

	protected Collection<JavaFileObject> addCompilationUnits(Map<String, String> qualifiedNameAndContent) {
		Collection<JavaFileObject> units = new ArrayList<JavaFileObject>(qualifiedNameAndContent.size());
		for (String qualifiedName : qualifiedNameAndContent.keySet()) {
			String sourceContent = qualifiedNameAndContent.get(qualifiedName);
			JavaFileObject sourceFile = addCompilationUnit(qualifiedName, sourceContent);
			units.add(sourceFile);
		}
		return units;
	}

	protected JavaFileObject addCompilationUnit(String qualifiedName, String sourceContent) {
		String packageName = qualifiedName.replaceFirst("^(?:(.*)[.][^.]+|[^.]+)$", "$1");
		String simpleClassName = qualifiedName.replaceFirst("^(.*[.])*([^.]+)$", "$2");
		VirtualSourceFileObject sourceFile = new VirtualSourceFileObject(simpleClassName, sourceContent);
		fileManager().addSourceFile(StandardLocation.SOURCE_PATH, packageName, simpleClassName, sourceFile);
		return sourceFile;
	}

	protected boolean runCompilationTask(CompilationTask task) {
		boolean success = task.call();
		if (!success) {
			System.err.println("[Compilation errors]");
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics().getDiagnostics()) {
				System.err.println(diagnostic.toString());
			}
			throw new DynamicCompilationException("Aborting: dynamic compilation failed");
		}
		return success;
	}

	private Map<String, byte[]> collectBytecodes(Map<String, String> qualifiedNameAndContent) {
		Map<String, byte[]> bytecodes = new HashMap<String, byte[]>();
		Map<String, VirtualClassFileObject> classFiles = fileManager().classFiles();
		for (String qualifiedName : classFiles.keySet()) {
			String topClassName = topClassName(qualifiedName);
			if (qualifiedNameAndContent.containsKey(topClassName)) {
				bytecodes.put(qualifiedName, classFiles.get(qualifiedName).byteCodes());
			}
		}
		return bytecodes;
	}

	private String topClassName(String qualifiedName) {
		return qualifiedName.split("[$]")[0];
	}

	private List<String> optionsWithClasspath(URL[] classpath) {
		List<String> options = new ArrayList<String>(options());
		options.add("-cp");
		String separator = String.valueOf(File.pathSeparatorChar);
		StringBuilder classpathString = new StringBuilder();
		for (URL path : classpath) {
			classpathString.append(path.getPath()).append(separator);
		}
		classpathString.deleteCharAt(classpathString.lastIndexOf(separator));
		options.add(classpathString.toString());
		return options;
	}

	private void log(String message) {
		System.out.println(message);
	}

	protected VirtualFileObjectManager fileManager() {
		return fileManager;
	}

	private List<String> options() {
		return options;
	}

	private JavaCompiler compiler() {
		return compiler;
	}

	private DiagnosticCollector<JavaFileObject> diagnostics() {
		return diagnostics;
	}

	private List<String> options;
	private JavaCompiler compiler;
	private VirtualFileObjectManager fileManager;
	private DiagnosticCollector<JavaFileObject> diagnostics;
}