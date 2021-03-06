package org.dynjs.cli;

import org.dynjs.api.Scope;
import org.dynjs.exception.DynJSException;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.DynThreadContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Repl {
    private final DynJS environment;
    private final DynThreadContext context;
    private final Scope scope;

    public Repl(DynJS environment, DynThreadContext context, Scope scope) {
        this.environment = environment;
        this.context = context;
        this.scope = scope;
    }

    public void run() {
        try {
            String NEW_LINE = System.getProperty("line.separator");
            StringBuilder consoleHello = new StringBuilder();
            consoleHello.append(NEW_LINE)
                    .append("dyn.js console.")
                    .append(NEW_LINE)
                    .append("Type exit and press ENTER to leave.")
                    .append(NEW_LINE);
            System.out.println(consoleHello.toString());
            while (true) {
                System.out.print("> ");
                String statement = input();
                if (statement.equals("exit")) {
                    return;
                } else {
                    try {
                        environment.eval(context, scope, statement);
                    } catch (DynJSException e) {
                        System.out.println(e.getClass().getSimpleName());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String input() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        return in.readLine();
    }
}
