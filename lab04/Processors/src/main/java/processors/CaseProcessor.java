package processors;

import processing.Processor;
import processing.StatusListener;
import processors.worker.ProcessWorker;
import processors.worker.Worker;

public class CaseProcessor implements Processor, Worker {
    private String taskToProcess;
    private String result = "";

    @Override
    public boolean submitTask(String s, StatusListener statusListener) {
        this.taskToProcess = s;

        return ProcessWorker.runTask(this, statusListener);
    }

    @Override
    public String getInfo() {
        return "Format Case #tekst #case | case: [camelCase, kebab-case, PascalCase]";
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public void work() {
        String[] split =  this.taskToProcess.split("#");
        String text = split[1].trim();
        String mode = split[2].substring(0, split[2].indexOf('|')).trim();

        this.result = switch (mode) {
            case "camelCase" -> convertToCamel(text);
            case "kebab-case" -> convertToKebab(text);
            case "PascalCase" -> convertToPascal(text);
            default -> "";
        };
    }

    private String convertToCamel(String s) {
        StringBuilder sb = new StringBuilder();
        boolean convertNext = true;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') {
                convertNext = false;
            } else if (convertNext) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toUpperCase(c));
                convertNext = true;
            }
        }

       return sb.toString();
    }

    private String convertToKebab(String s) {
        s = s.toLowerCase();

        return s.replace(" ", "-");
    }

    private String convertToPascal(String s) {
        StringBuilder sb = new StringBuilder();
        boolean convertNext = true;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (convertNext) {
                sb.append(Character.toUpperCase(c));
                convertNext = false;
            } else if (c == ' ') {
                convertNext = true;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }
}
