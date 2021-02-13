package com.gilsoft.graalvm;

import org.graalvm.polyglot.*;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    private static Value loadSource(Context polyglotCtx, String language, String fileName) throws IOException {
        File file = new File(fileName);
        Source source = Source.newBuilder(language, file).build();
        return polyglotCtx.eval(source);
    }

    public static void main(String[] args) throws IOException {
        int iterations = 3;
        var iterationsEnv = System.getenv("ITERATIONS");
        if (iterationsEnv != null) {
            iterations = Integer.parseInt(iterationsEnv);
        }
        int argument = 5;
        var argumentEnv = System.getenv("ARGUMENT");
        if (argumentEnv != null) {
            argument = Integer.parseInt(argumentEnv);
        }
        System.out.println("Running " + iterations + " with argument " + argument);
        String benchmarkScript = "fannkuch.py";
        if (args.length > 1) {
            benchmarkScript = args[1];
        }
        try (Context context = Context.create()) {
            long scriptStart = System.currentTimeMillis();
            var source = loadSource(context, "python", benchmarkScript);
            System.out.println("Took " + (System.currentTimeMillis() - scriptStart) + "ms to load the script");
            long start = System.currentTimeMillis();
            System.out.println("Loaded code");
            Value benchmarkFunction = context.getBindings("python").getMember("fannkuch");
            for (int i = 0; i < iterations; i++) {
                System.out.println(System.currentTimeMillis() + " " + i + " " + benchmarkFunction.execute(argument).asInt());
            }
            System.out.println("Executed benchmark in " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
